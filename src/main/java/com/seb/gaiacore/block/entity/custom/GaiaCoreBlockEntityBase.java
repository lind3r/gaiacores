package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.custom.GaiaCoreBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import static com.seb.gaiacore.block.custom.GaiaCoreBase.POWERED;

public abstract class GaiaCoreBlockEntityBase extends BlockEntity {
    protected final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 10;
        }
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    protected boolean anchored = true;
    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 100;

    public GaiaCoreBlockEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> GaiaCoreBlockEntityBase.this.progress;
                    case 1 -> GaiaCoreBlockEntityBase.this.maxProgress;
                    default -> 0;
                };
            }
            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> GaiaCoreBlockEntityBase.this.progress = value;
                    case 1 -> GaiaCoreBlockEntityBase.this.maxProgress = value;
                }
            }
            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        tag.put("inventory", itemHandler.serializeNBT(provider));
        tag.putInt("core.progress", progress);
        tag.putInt("core.max_progress", maxProgress);
        tag.putBoolean("core.is_anchored", anchored);
        super.saveAdditional(tag, provider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("inventory")) {
            itemHandler.deserializeNBT(provider, tag.getCompound("inventory"));
        }
        progress = tag.getInt("core.progress");
        maxProgress = tag.getInt("core.max_progress");
        setAnchored(tag.getBoolean("core.is_anchored"));
    }

    public boolean isAnchored() {
        return anchored;
    }

    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            if (!anchored) {
                BlockState current = level.getBlockState(getBlockPos());
                if (current.hasProperty(GaiaCoreBase.ANCHORED)) {
                    BlockState updated = current.setValue(GaiaCoreBase.ANCHORED, false);
                    System.out.println("anchored set to false");
                    level.setBlock(getBlockPos(), updated, 3);
                }
            }
        }
    }

    public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Nullable
    protected abstract String getCoreTranslationKey();
    protected void resetProgress() {
        progress = 0;
    }
    protected abstract boolean coreSpecificConditionsMet(BlockState blockState);
    protected abstract void onProgressComplete(Level level, BlockPos blockPos, BlockState blockState);
    protected abstract void makeSound(Level level, BlockPos blockPos);

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (!coreSpecificConditionsMet(blockState)) {
            blockState.setValue(POWERED, false);
            return;
        }

        progress++;
        level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, true));
        setChanged(level, blockPos, blockState);
        if (progress >= maxProgress) {
            onProgressComplete(level, blockPos, blockState);
            makeSound(level, blockPos);
            resetProgress();
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}
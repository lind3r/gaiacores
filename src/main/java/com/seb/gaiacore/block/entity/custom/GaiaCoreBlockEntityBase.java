package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.custom.GaiaCoreBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

public abstract class GaiaCoreBlockEntityBase extends BlockEntity implements MenuProvider {
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

    protected boolean isDormant = true;
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        tag.put("inventory", itemHandler.serializeNBT(provider));
        tag.putInt("core.progress", progress);
        tag.putInt("core.max_progress", maxProgress);
        tag.putBoolean("core.is_dormant", isDormant);
        super.saveAdditional(tag, provider);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("inventory")) {
            itemHandler.deserializeNBT(provider, tag.getCompound("inventory"));
        }
        progress = tag.getInt("core.progress");
        maxProgress = tag.getInt("core.max_progress");
        isDormant = tag.getBoolean("core.is_dormant");
    }

    public boolean isDormant() {
        return isDormant;
    }

    public void setDormant(boolean dormant) {
        this.isDormant = dormant;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Nullable
    public abstract AbstractContainerMenu createMenu(int id, net.minecraft.world.entity.player.Inventory playerInventory, Player player);
    protected abstract String getCoreTranslationKey();
    protected void resetProgress() {
        progress = 0;
    }
    protected abstract boolean customConditionsMet(BlockState blockState);
    protected abstract void checkForDormantBreaker();
    protected abstract void onProgressComplete(Level level, BlockPos blockPos, BlockState blockState);
    protected abstract void makeSound(Level level, BlockPos blockPos);

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (isDormant && !blockState.getValue(GaiaCoreBase.POWERED)) { // TODO temp POWERED check for debugging
            checkForDormantBreaker();
            return;
        }

        if (!blockState.getValue(GaiaCoreBase.POWERED)) {
            return;
        }

        progress++;
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

    @Override
    public Component getDisplayName() {
        return Component.translatable(getCoreTranslationKey());
    }
}
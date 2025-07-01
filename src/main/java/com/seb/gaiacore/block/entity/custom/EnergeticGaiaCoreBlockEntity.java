package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.custom.GaiaCoreBase;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.screen.custom.EnergeticGaiaCoreMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class EnergeticGaiaCoreBlockEntity extends BlockEntity implements MenuProvider {

    public EnergeticGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ENERGETIC_GAIA_CORE_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> EnergeticGaiaCoreBlockEntity.this.progress;
                    case 1 -> EnergeticGaiaCoreBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0: EnergeticGaiaCoreBlockEntity.this.progress = value;
                    case 1: EnergeticGaiaCoreBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 10;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    private static final List<BlockState> LOW_TIER_ORES = List.of(
        Blocks.COAL_ORE.defaultBlockState(),
        Blocks.IRON_ORE.defaultBlockState(),
        Blocks.COPPER_ORE.defaultBlockState()
    );
    private static final Random RANDOM = new Random();

    public void clearContents() {
        itemHandler.setStackInSlot(0, ItemStack.EMPTY);
    }

    public ItemStack getStoredItem() {
        return itemHandler.getStackInSlot(0);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
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
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
        pTag.putInt("energetic_gaia_core.progress", progress);
        pTag.putInt("energetic_gaia_core.max_progress", maxProgress);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("energetic_gaia_core.progress");
        maxProgress = pTag.getInt("energetic_gaia_core.max_progress");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new EnergeticGaiaCoreMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (conditionsMet(blockState)) {
            progress++;
            setChanged(level, blockPos, blockState);
            if (progress >= maxProgress) {
                spawnLowTierOres(level);
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private boolean conditionsMet(BlockState blockState) {
        if (!blockState.getValue(GaiaCoreBase.POWERED)) {
            return false;
        }
        // At least one adjacent block must be air
        for (Direction dir : Direction.values()) {
            BlockPos adjacentPos = worldPosition.relative(dir);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            if (adjacentState.isAir()) {
                return true;
            }
        }
        return false;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void spawnLowTierOres(Level level) {
        if (level == null || level.isClientSide) return;

        for (Direction dir : Direction.values()) {
            BlockPos targetPos = worldPosition.relative(dir);
            BlockState targetState = level.getBlockState(targetPos);

            // Only place if the target block is air
            if (targetState.isAir()) {
                BlockState ore = LOW_TIER_ORES.get(RANDOM.nextInt(LOW_TIER_ORES.size()));
                level.setBlock(targetPos, ore, 3);
            }
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
        return Component.translatable("block.gaiacore.energetic_gaia_core");
    }
}

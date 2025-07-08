package com.seb.gaiacores.block.entity.custom;

import com.seb.gaiacores.block.custom.GaiaCoreBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class GaiaCoreBlockEntityBase extends BlockEntity {

    protected boolean anchored = true;
    protected int progress = 0;
    protected int maxProgress = 100;
    protected int cooldown = 0;
    protected int defaultCooldown = 20; // 1 second at 20 TPS

    public GaiaCoreBlockEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("core.progress", progress);
        tag.putInt("core.max_progress", maxProgress);
        tag.putBoolean("core.is_anchored", anchored);
        tag.putInt("core.cooldown", cooldown);
        super.saveAdditional(tag, provider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        progress = tag.getInt("core.progress");
        maxProgress = tag.getInt("core.max_progress");
        setAnchored(tag.getBoolean("core.is_anchored"));
        cooldown = tag.getInt("core.cooldown");
    }

    public void onExplosionNearby() {
        if (level == null || level.isClientSide) return;
        setAnchored(false);
    }

    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            if (!anchored) {
                BlockState current = level.getBlockState(getBlockPos());
                if (current.getValue(GaiaCoreBase.ANCHORED)) {
                    BlockState updated = current.setValue(GaiaCoreBase.ANCHORED, false);
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

    protected void makeSound(Level level, BlockPos blockPos) {}

    protected void setCooldown(int ticks) {
        this.cooldown = ticks;
    }

    protected boolean isOnCooldown() {
        return cooldown > 0;
    }

    protected void reduceCooldownIf(boolean condition) {
        if (condition && cooldown > 0) {
            cooldown--;
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
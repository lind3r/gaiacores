package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import com.seb.gaiacore.block.custom.GaiaCoreBase;

public class VolcanicGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public VolcanicGaiaCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOLCANIC_GAIA_CORE_BE.get(), pos, state);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;

        if (isOnCooldown()) {
            cooldown--;
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, false));
            return;
        }

        // Check for Monster above
        BlockPos above = blockPos.above();
        AABB aboveBox = new AABB(above);
        boolean hasMonster = !level.getEntitiesOfClass(Mob.class, aboveBox,
                mob -> mob.getType().getCategory() == MobCategory.MONSTER).isEmpty();

        if (hasMonster) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, true));
        } else {
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, false));
        }
    }

    public void onHostileEntityKilled(LivingDeathEvent event) {
        if (event.getEntity().getType().getCategory() != MobCategory.MONSTER) return;
        if (!event.getSource().getMsgId().equals("lava")) return;
        if (isOnCooldown()) return;

        spawnLava();

        setCooldown(defaultCooldown);
    }

    private void spawnLava() {
        if (level == null) return;

        Direction groundFacing = Direction.DOWN;
        BlockPos groundPos = worldPosition.relative(groundFacing);
        BlockState targetState = level.getBlockState(groundPos);
        if (targetState.isAir()) {
            level.setBlock(groundPos, Blocks.LAVA.defaultBlockState(), 3);
        }
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.volcanic_gaia_core";
    }
}
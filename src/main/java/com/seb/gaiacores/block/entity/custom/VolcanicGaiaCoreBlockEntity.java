package com.seb.gaiacores.block.entity.custom;

import com.seb.gaiacores.Config;
import com.seb.gaiacores.block.custom.GaiaCoreBase;
import com.seb.gaiacores.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class VolcanicGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public VolcanicGaiaCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOLCANIC_GAIA_CORE_BE.get(), pos, state);
    }

    private BlockPos blockPos;

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;
        this.blockPos = blockPos;

        if (isOnCooldown()) {
            cooldown--;
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

        if (!isOnCooldown()) {
            spawnLava();
        }
    }

    private void spawnLava() {
        if (level == null) return;

        Direction groundFacing = Direction.DOWN;
        BlockPos groundPos = worldPosition.relative(groundFacing);
        if (level.isEmptyBlock(groundPos)) {
            level.setBlock(groundPos, Blocks.LAVA.defaultBlockState(), 3);
            makeSound(level, blockPos);
            setCooldown(Config.getVolcanicCoreCooldown());
        }
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacores.volcanic_gaia_core";
    }
}
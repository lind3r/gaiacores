package com.seb.gaiacores.block.entity.custom;

import com.seb.gaiacores.Config;
import com.seb.gaiacores.block.custom.GaiaCoreBase;
import com.seb.gaiacores.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class CharredGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public CharredGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CHARRED_GAIA_CORE_BE.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;

        List<Animal> animals = getAnimalsAbove(level, blockPos);
        boolean hasAnimal = !animals.isEmpty();

        if (blockState.getValue(GaiaCoreBase.POWERED) != hasAnimal) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, hasAnimal));
        }

        if (isOnCooldown()) {
            cooldown--;
            return;
        }

        if (hasAnimal) {
            turnAnimalIntoCoal(level, blockPos, blockState, animals.getFirst());
        }
    }

    private List<Animal> getAnimalsAbove(Level level, BlockPos blockPos) {
        BlockPos above = blockPos.above();
        return level.getEntitiesOfClass(Animal.class, new AABB(above));
    }

    private void turnAnimalIntoCoal(Level level, BlockPos blockPos, BlockState blockState, Animal animal) {
        if (!animal.isRemoved() && animal.isAlive()) {
            animal.discard();
            smoke(level);
            ItemEntity coal = new ItemEntity(level, animal.getX(), animal.getY(), animal.getZ(), Items.COAL.getDefaultInstance());
            level.addFreshEntity(coal);
            makeSound(level, blockPos);
            setCooldown(Config.getCharredCoreCooldown());
            setChanged(level, blockPos, blockState);
        }
    }

    private void smoke(Level level) {
        level.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                worldPosition.getX() + 0.5,
                worldPosition.getY() + 1.0,
                worldPosition.getZ() + 0.5,
                0.0, 0.05, 0.0);
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacores.charred_gaia_core";
    }
}

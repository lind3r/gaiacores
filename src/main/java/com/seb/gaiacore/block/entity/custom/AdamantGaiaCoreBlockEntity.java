package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.Config;
import com.seb.gaiacore.block.custom.GaiaCoreBase;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AdamantGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public AdamantGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ADAMANT_GAIA_CORE_BE.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;

        BlockPos above = blockPos.above();
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(above));

        ItemEntity coalItem = null;
        for (ItemEntity item : items) {
            if (item.getItem().is(Items.COAL) && item.getItem().getCount() > 0) {
                coalItem = item;
                break;
            }
        }

        boolean hasCoal = coalItem != null;

        reduceCooldownIf(hasCoal);

        if (!isOnCooldown() && hasCoal) {
            BlockPos coalPos = convertCoalToDiamond(level, coalItem);
            Entity creeper = spawnCreeper(level, coalPos);
            handleCreeperExplosion(creeper, level, blockPos);
            setCooldown(Config.getAdamantCoreCooldown());
            makeSound(level, blockPos);
        }

        if (blockState.getValue(GaiaCoreBase.POWERED) != hasCoal) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, hasCoal));
        }

        setChanged(level, blockPos, blockState);
    }

    private BlockPos convertCoalToDiamond(Level level, ItemEntity coalItem) {
        coalItem.getItem().shrink(1);
        if (coalItem.getItem().isEmpty()) coalItem.discard();

        BlockPos pos = coalItem.blockPosition();
        ItemEntity diamond = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, Items.DIAMOND.getDefaultInstance());
        level.addFreshEntity(diamond);
        return pos;
    }

    private Entity spawnCreeper(Level level, BlockPos pos) {
        Creeper creeper = new Creeper(EntityType.CREEPER, level);
        creeper.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, level.random.nextFloat() * 360F, 0.0F);
        level.addFreshEntity(creeper);
        return creeper;
    }

    private void handleCreeperExplosion(Entity creeper, Level level, BlockPos blockPos) {
        List<Creeper> creepers = level.getEntitiesOfClass(Creeper.class, new AABB(blockPos).inflate(10));
        if (creepers.size() >= 10) {
            level.explode(creeper, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 8.0F, Level.ExplosionInteraction.TNT);
        }
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.adamant_gaia_core";
    }
}

package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.util.ModBlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class VerdantGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public VerdantGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VERDANT_GAIA_CORE_BE.get(), pPos, pBlockState);
        setCooldown(20);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;

        ItemPair items = findRequiredItems(level);

        reduceCooldownIf(items != null);

        if (isOnCooldown()) return;
        if (items == null) return;

        consumeItemsAndGrowTree(level, blockPos, items.plankEntity, items.stickEntity);
    }

    private ItemPair findRequiredItems(Level level) {
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class,
                new net.minecraft.world.phys.AABB(worldPosition));
        ItemEntity plankEntity = null;
        ItemEntity stickEntity = null;

        for (ItemEntity item : items) {
            if (plankEntity == null && item.getItem().is(Items.OAK_PLANKS)) {
                plankEntity = item;
            } else if (stickEntity == null && item.getItem().is(Items.STICK)) {
                stickEntity = item;
            }
        }
        if (plankEntity != null && stickEntity != null) {
            return new ItemPair(plankEntity, stickEntity);
        }
        return null;
    }

    private void consumeItemsAndGrowTree(Level level, BlockPos blockPos, ItemEntity plankEntity, ItemEntity stickEntity) {
        plankEntity.getItem().shrink(1);
        if (plankEntity.getItem().isEmpty()) plankEntity.discard();
        stickEntity.getItem().shrink(1);
        if (stickEntity.getItem().isEmpty()) stickEntity.discard();

        if (canGrowTree(level, blockPos)) {
            setCooldown(defaultCooldown);
        }
    }

    private boolean canGrowTree(Level level, BlockPos blockPos) {
        Direction skyFacing = ModBlockPosHelper.findSkyFacing(level, worldPosition);
        if (skyFacing != null) {
            BlockPos treePos = worldPosition.relative(skyFacing);
            BlockState saplingState = Blocks.OAK_SAPLING.defaultBlockState();
            level.setBlock(treePos, saplingState, 3);
            if (level instanceof ServerLevel serverLevel) {
                ((SaplingBlock) Blocks.OAK_SAPLING).advanceTree(serverLevel, treePos, saplingState, serverLevel.random);
                makeSound(level, blockPos);
            }
            return true;
        }
        return false;
    }

    private static class ItemPair {
        final ItemEntity plankEntity;
        final ItemEntity stickEntity;
        ItemPair(ItemEntity plankEntity, ItemEntity stickEntity) {
            this.plankEntity = plankEntity;
            this.stickEntity = stickEntity;
        }
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.verdant_gaia_core";
    }
}

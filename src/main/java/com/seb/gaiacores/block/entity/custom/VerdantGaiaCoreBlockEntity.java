package com.seb.gaiacores.block.entity.custom;

import com.seb.gaiacores.Config;
import com.seb.gaiacores.block.custom.GaiaCoreBase;
import com.seb.gaiacores.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class VerdantGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public VerdantGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VERDANT_GAIA_CORE_BE.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;

        BlockPos above = blockPos.above();

        AABB box = new AABB(blockPos).inflate(1);
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, box);

        ItemEntity plankEntity = null;
        ItemEntity stickEntity = null;

        for (ItemEntity item : items) {
            if (plankEntity == null && item.getItem().is(Items.OAK_PLANKS)) plankEntity = item;
            else if (stickEntity == null && item.getItem().is(Items.STICK)) stickEntity = item;
        }

        boolean hasAny = plankEntity != null || stickEntity != null;
        boolean hasBoth = plankEntity != null && stickEntity != null;
        boolean shouldBePowered = hasAny;

        BlockState aboveState = level.getBlockState(above);
        boolean saplingOrTreePresent = !level.isEmptyBlock(above)
                && (aboveState.is(Blocks.OAK_SAPLING)
                || aboveState.is(Blocks.OAK_LOG)
                || aboveState.is(Blocks.BIRCH_LOG)
                || aboveState.is(Blocks.SPRUCE_LOG)
                || aboveState.is(Blocks.JUNGLE_LOG)
                || aboveState.is(Blocks.DARK_OAK_LOG)
                || aboveState.is(Blocks.ACACIA_LOG));

        if (!isOnCooldown() && hasBoth && !saplingOrTreePresent) {
            consumeItems(plankEntity, stickEntity);
            placeDirtAndGrowTree(level, blockPos);
            setCooldown(Config.getVerdantCoreCooldown());
        }

        if (blockState.getValue(GaiaCoreBase.POWERED) != shouldBePowered) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, shouldBePowered));
        }

        setChanged(level, blockPos, blockState);

        if (isOnCooldown()) cooldown--;
    }

    private void consumeItems(ItemEntity plankEntity, ItemEntity stickEntity) {
        if (plankEntity != null) {
            plankEntity.getItem().shrink(1);
            if (plankEntity.getItem().isEmpty()) plankEntity.discard();
        }
        if (stickEntity != null) {
            stickEntity.getItem().shrink(1);
            if (stickEntity.getItem().isEmpty()) stickEntity.discard();
        }
    }

    private void placeDirtAndGrowTree(Level level, BlockPos corePos) {
        BlockPos dirtPos = corePos.above();
        BlockPos treePos = dirtPos.above();

        if (level instanceof ServerLevel serverLevel) {
            level.setBlock(dirtPos, Blocks.DIRT.defaultBlockState(), 3);

            TreeGrower grower = TreeGrower.OAK;
            BlockState fakeState = Blocks.OAK_SAPLING.defaultBlockState().setValue(SaplingBlock.STAGE, 1);

            grower.growTree(
                    serverLevel,
                    serverLevel.getChunkSource().getGenerator(),
                    treePos,
                    fakeState,
                    serverLevel.getRandom()
            );

            makeSound(level, corePos);
        }
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacores.verdant_gaia_core";
    }
}

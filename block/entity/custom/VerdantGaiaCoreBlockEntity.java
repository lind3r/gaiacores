// ...existing code...

public class VerdantGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public VerdantGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VERDANT_GAIA_CORE_BE.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;
        if (isOnCooldown()) return;

        ItemPair items = findRequiredItems(level);
        if (items == null) return;

        consumeItemsAndGrowTree(level, items.plankEntity, items.stickEntity);
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

    private void consumeItemsAndGrowTree(Level level, ItemEntity plankEntity, ItemEntity stickEntity) {
        // Consume one of each
        plankEntity.getItem().shrink(1);
        if (plankEntity.getItem().isEmpty()) plankEntity.discard();
        stickEntity.getItem().shrink(1);
        if (stickEntity.getItem().isEmpty()) stickEntity.discard();

        if (canGrowTree(level)) {
            setCooldown(defaultCooldown);
        }
    }

    private boolean canGrowTree(Level level) {
        Direction skyFacing = ModBlockPosHelper.findSkyFacing(level, worldPosition);
        if (skyFacing != null) {
            BlockPos treePos = worldPosition.relative(skyFacing);
            BlockState saplingState = Blocks.OAK_SAPLING.defaultBlockState();
            level.setBlock(treePos, saplingState, 3);
            if (level instanceof ServerLevel serverLevel) {
                ((SaplingBlock) Blocks.OAK_SAPLING).advanceTree(serverLevel, treePos, saplingState, serverLevel.random);
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
        // ...existing code...
    }

    @Override
    protected String getCoreTranslationKey() {
        // ...existing code...
    }
}


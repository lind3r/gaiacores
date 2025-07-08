package com.seb.gaiacores.block.custom;

import com.seb.gaiacores.block.entity.custom.GaiaCoreBlockEntityBase;
import com.seb.gaiacores.component.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public abstract class GaiaCoreBase extends BaseEntityBlock {
    protected final GaiaCoreVariant gaiaCoreVariant;

    public GaiaCoreBase(Properties properties, GaiaCoreVariant variant) {
        super(properties);
        this.gaiaCoreVariant = variant;
        this.registerDefaultState(this.defaultBlockState()
                .setValue(POWERED, false)
                .setValue(ANCHORED, true));
    }

    public enum GaiaCoreVariant { LUCENT, VOLCANIC, VERDANT, CHARRED, ADAMANT }
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty ANCHORED = BooleanProperty.create("anchored");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED, ANCHORED);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos,
                        BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be != null) {
                try {
                    be.getClass().getMethod("drops").invoke(be);
                } catch (Exception ignored) {}
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be != null) {
            if (be instanceof MenuProvider provider && pPlayer instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(provider, pPos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        // Unbreakable when anchored, normal otherwise
        return state.getValue(ANCHORED) ? 0.0f : super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        ItemStack stack = new ItemStack(this);

        if (be instanceof GaiaCoreBlockEntityBase coreBE) {
            CompoundTag tag = new CompoundTag();
            coreBE.saveAdditional(tag, null);

            stack.set(ModDataComponentTypes.BLOCK_ENTITY_NBT.get(), tag);
        }

        return List.of(stack);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof GaiaCoreBlockEntityBase coreBE) {
                CompoundTag tag = stack.get(ModDataComponentTypes.BLOCK_ENTITY_NBT.get());
                if (tag != null) {
                    HolderLookup.Provider provider = level.getServer() != null ? level.getServer().registries().compositeAccess() : null;
                    coreBE.loadAdditional(tag, provider);
                }
            }
        }
    }
}

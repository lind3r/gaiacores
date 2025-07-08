package com.seb.gaiacores.screen.custom;

import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.block.entity.custom.GaiaCoreAnalyzerBlockEntity;
import com.seb.gaiacores.screen.ModMenuTypes;
import com.seb.gaiacores.util.ModMenuHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class GaiaCoreAnalyzerMenu extends AbstractContainerMenu {
    public final GaiaCoreAnalyzerBlockEntity blockEntity;
    private final Level level;

    public GaiaCoreAnalyzerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public GaiaCoreAnalyzerMenu(int pContainerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.GAIA_CORE_ANALYZER_MENU.get(), pContainerId);
        this.blockEntity = ((GaiaCoreAnalyzerBlockEntity) blockEntity);
        this.level = inv.player.level();

        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 0, 18, 35));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    private static final int TE_INVENTORY_SLOT_COUNT = 1;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        return ModMenuHelper.quickMoveStackLogic(
            this, playerIn, pIndex, TE_INVENTORY_SLOT_COUNT,
            this::moveItemStackTo
        );
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.GAIA_CORE_ANALYZER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < ModMenuHelper.PLAYER_INVENTORY_ROW_COUNT; ++i) {
            for (int l = 0; l < ModMenuHelper.PLAYER_INVENTORY_COLUMN_COUNT; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < ModMenuHelper.HOTBAR_SLOT_COUNT; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public GaiaCoreAnalyzerBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
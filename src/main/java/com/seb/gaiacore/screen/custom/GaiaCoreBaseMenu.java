package com.seb.gaiacore.screen.custom;

import com.seb.gaiacore.util.ModMenuHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class GaiaCoreBaseMenu<T extends BlockEntity> extends AbstractContainerMenu {
    public final T blockEntity;
    protected final Level level;
    protected final ContainerData data;

    protected GaiaCoreBaseMenu(MenuType<?> menuType, int containerId, Inventory inv, T blockEntity, ContainerData data) {
        super(menuType, containerId);
        this.blockEntity = blockEntity;
        this.level = inv.player.level();
        this.data = data;
    }

    protected void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < ModMenuHelper.PLAYER_INVENTORY_ROW_COUNT; ++i) {
            for (int l = 0; l < ModMenuHelper.PLAYER_INVENTORY_COLUMN_COUNT; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    protected void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < ModMenuHelper.HOTBAR_SLOT_COUNT; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    protected boolean stillValid(Player player, Block block) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, block);
    }

    protected static final int TE_INVENTORY_SLOT_COUNT = 1;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        return ModMenuHelper.quickMoveStackLogic(
            this, playerIn, pIndex, TE_INVENTORY_SLOT_COUNT,
            this::moveItemStackTo
        );
    }

    public boolean isProgressing() {
        return this.data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        if (maxProgress == 0) {
            maxProgress = 1;
        }
        return (int) ((float) progress / maxProgress * 24);
    }
}

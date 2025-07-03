package com.seb.gaiacore.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ModMenuHelper {
    public static final int HOTBAR_SLOT_COUNT = 9;
    public static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    public static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    public static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    public static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    public static final int VANILLA_FIRST_SLOT_INDEX = 0;

    public static int getTeInventoryFirstSlotIndex() {
        return VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    }

    /**
     * This method cannot call moveItemStackTo directly due to protected access.
     * Instead, call this logic from within your menu class and delegate to this helper for the index math.
     */
    public static ItemStack quickMoveStackLogic(AbstractContainerMenu menu, Player playerIn, int pIndex, int teInventorySlotCount,
                                                QuickMoveHandler moveHandler) {
        int vanillaSlotCount = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
        int teFirstSlotIndex = getTeInventoryFirstSlotIndex();

        Slot sourceSlot = menu.slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < vanillaSlotCount) {
            if (!moveHandler.move(sourceStack, teFirstSlotIndex, teFirstSlotIndex + teInventorySlotCount, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < teFirstSlotIndex + teInventorySlotCount) {
            if (!moveHandler.move(sourceStack, VANILLA_FIRST_SLOT_INDEX, vanillaSlotCount, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @FunctionalInterface
    public interface QuickMoveHandler {
        boolean move(ItemStack stack, int start, int end, boolean reverse);
    }
}
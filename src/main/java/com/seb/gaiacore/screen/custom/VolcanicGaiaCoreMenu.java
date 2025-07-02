package com.seb.gaiacore.screen.custom;

import com.seb.gaiacore.block.ModBlocks;
import com.seb.gaiacore.block.entity.custom.VolcanicGaiaCoreBlockEntity;
import com.seb.gaiacore.screen.ModMenuTypes;
import com.seb.gaiacore.util.ModMenuHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class VolcanicGaiaCoreMenu extends GaiaCoreBaseMenu<VolcanicGaiaCoreBlockEntity> {
    public VolcanicGaiaCoreMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new net.minecraft.world.inventory.SimpleContainerData(2));
    }

    public VolcanicGaiaCoreMenu(int pContainerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.VOLCANIC_GAIA_CORE_MENU.get(), pContainerId, inv, (VolcanicGaiaCoreBlockEntity) blockEntity, data);
        this.addSlot(new SlotItemHandler(this.blockEntity.getItemHandler(), 0, 54, 34));
        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(pPlayer, ModBlocks.VOLCANIC_GAIA_CORE.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        return ModMenuHelper.quickMoveStackLogic(
            this, playerIn, pIndex, TE_INVENTORY_SLOT_COUNT,
            this::moveItemStackTo
        );
    }
}
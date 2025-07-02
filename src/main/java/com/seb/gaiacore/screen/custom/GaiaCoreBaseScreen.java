package com.seb.gaiacore.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.seb.gaiacore.GaiaCore;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class GaiaCoreBaseScreen<T extends GaiaCoreBaseMenu<?>> extends AbstractContainerScreen<T> {
    protected final ResourceLocation guiTexture;
    protected static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "textures/gui/arrow_progress.png");

    public GaiaCoreBaseScreen(T menu, Inventory playerInventory, Component title,
                              ResourceLocation guiTexture, ResourceLocation arrowTexture) {
        super(menu, playerInventory, title);
        this.guiTexture = guiTexture;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, guiTexture);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(guiTexture, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pGuiGraphics, x, y);
    }

    protected void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isProgressing()) {
            guiGraphics.blit(ARROW_TEXTURE, x + 73, y + 35, 0, 0, menu.getScaledProgress(), 16, 24, 16);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}

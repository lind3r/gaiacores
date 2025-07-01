package com.seb.gaiacore.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.component.ModDataComponentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GaiaCoreAnalyzerScreen extends AbstractContainerScreen<GaiaCoreAnalyzerMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "textures/gui/gaia_core_analyzer/gaia_core_analyzer_gui.png");

    public GaiaCoreAnalyzerScreen(GaiaCoreAnalyzerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        float textScale = 0.6f;

        ItemStack storedItem = menu.getBlockEntity().getStoredItem();
        String translationKey;
        if (storedItem == null || storedItem.isEmpty()) {
            translationKey = "block.gaiacore.gaia_core_scanner_insert_item";
        } else if (storedItem.has(ModDataComponentTypes.BLOCK_TRANSLATION_KEY.get())) {
            String analyzedBlockTranslationKey = (String) storedItem.get(ModDataComponentTypes.BLOCK_TRANSLATION_KEY.get());
            translationKey = switch (analyzedBlockTranslationKey) {
                case "block.gaiacore.energetic_gaia_core" -> "block.gaiacore.energetic_gaia_core_analyze_text";
                case "block.gaiacore.volcanic_gaia_core" -> "block.gaiacore.volcanic_gaia_core_analyze_text";
                default -> "block.gaiacore.gaia_core_scanner_invalid_block_scanned";
            };
        } else {
            translationKey = "block.gaiacore.gaia_core_scanner_invalid_block_scanned";
        }
        Component textComponent = Component.translatable(translationKey);

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(x + 59, y + 21, 0); // Move to text position
        pGuiGraphics.pose().scale(textScale, textScale, 1.0F); // Scale text

        int textBoxWidth = 150;
        List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(textComponent, textBoxWidth);
        for (int i = 0; i < lines.size(); i++) {
            int newY = i * Minecraft.getInstance().font.lineHeight;
            pGuiGraphics.drawString(Minecraft.getInstance().font, lines.get(i), 0, newY, 0xFFFFFF, false);
        }
        pGuiGraphics.pose().popPose();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
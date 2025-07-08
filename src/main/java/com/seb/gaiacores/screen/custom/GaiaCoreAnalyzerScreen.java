package com.seb.gaiacores.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.component.ModDataComponentTypes;
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
            ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "textures/gui/gaia_core_analyzer/gaia_core_analyzer_gui.png");

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

        float textScale = 0.7f;
        int textBoxWidth = 140;

        ItemStack storedItem = menu.getBlockEntity().getStoredItem();
        String translationKey;
        if (storedItem == null || storedItem.isEmpty()) {
            translationKey = "block.gaiacores.gaia_core_scanner_insert_item";
        } else if (storedItem.has(ModDataComponentTypes.BLOCK_TRANSLATION_KEY.get())) {
            String analyzedBlockTranslationKey = (String) storedItem.get(ModDataComponentTypes.BLOCK_TRANSLATION_KEY.get());
            translationKey = switch (analyzedBlockTranslationKey) {
                case "block.gaiacores.lucent_gaia_core" -> "block.gaiacores.lucent_gaia_core_analyze_text";
                case "block.gaiacores.volcanic_gaia_core" -> "block.gaiacores.volcanic_gaia_core_analyze_text";
                case "block.gaiacores.verdant_gaia_core" -> "block.gaiacores.verdant_gaia_core_analyze_text";
                case "block.gaiacores.charred_gaia_core" -> "block.gaiacores.charred_gaia_core_analyze_text";
                case "block.gaiacores.adamant_gaia_core" -> "block.gaiacores.adamant_gaia_core_analyze_text";
                default -> "block.gaiacores.gaia_core_scanner_invalid_block_scanned";
            };
        } else {
            translationKey = "block.gaiacores.gaia_core_scanner_invalid_block_scanned";
        }

        Component textComponent = Component.translatable(translationKey);
        List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(textComponent, textBoxWidth);
        totalLines = lines.size();

        // Begin text rendering
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(x + 46, y + 21, 0); // Position for text
        pGuiGraphics.pose().scale(textScale, textScale, 1.0F); // Text scale

        int fontHeight = Minecraft.getInstance().font.lineHeight;

        for (int i = scrollOffset; i < Math.min(scrollOffset + maxVisibleLines, lines.size()); i++) {
            int newY = (i - scrollOffset) * fontHeight;
            pGuiGraphics.drawString(Minecraft.getInstance().font, lines.get(i), 0, newY, 0xFFFFFF, false);
        }

        pGuiGraphics.pose().popPose();
    }

    private int scrollOffset = 0;
    private int maxVisibleLines = 7;
    private int totalLines = 0;

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        int maxScroll = Math.max(0, totalLines - maxVisibleLines);

        if (pScrollY > 0) {
            scrollOffset = Math.max(0, scrollOffset - 1);
        } else if (pScrollY < 0) {
            scrollOffset = Math.min(maxScroll, scrollOffset + 1);
        }

        return true;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
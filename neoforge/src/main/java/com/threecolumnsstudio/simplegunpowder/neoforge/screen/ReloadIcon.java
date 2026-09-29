package com.threecolumnsstudio.simplegunpowder.neoforge.screen;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class ReloadIcon extends AbstractWidget {

    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(SimpleGunpowder.MOD_ID, "textures/gui/restart_required.png");
    private static final int ICON_SIZE = 10;

    public ReloadIcon(Component tooltip) {
        super(0, 0, ICON_SIZE, 20, tooltip);
        setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int iconY = getY() + (getHeight() - ICON_SIZE) / 2;
        graphics.blit(TEXTURE, getX(), iconY, 0.0F, 0.0F, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }
}

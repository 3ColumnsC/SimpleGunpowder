package com.threecolumnsstudio.simplegunpowder.fabric.screen;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class ReloadIcon extends ClickableWidget {

    private static final Identifier TEXTURE =
        Identifier.of(SimpleGunpowder.MOD_ID, "textures/gui/restart_required.png");
    private static final int ICON_SIZE = 10;

    public ReloadIcon(Text tooltip) {
        super(0, 0, ICON_SIZE, 20, tooltip);
        setTooltip(Tooltip.of(tooltip));
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int iconY = this.getY() + (this.getHeight() - ICON_SIZE) / 2;
        context.drawTexture(TEXTURE, this.getX(), iconY, 0.0F, 0.0F, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }
}

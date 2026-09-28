package com.threecolumnsstudio.simplegunpowder.client.screen;

import java.util.function.Consumer;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.ScrollableLayout;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public final class SimpleGunpowderConfigScreen extends Screen {

    private static final int WIDGET_WIDTH = 300;
    private static final int WIDGET_HEIGHT = 20;
    private static final int ROW_SPACING = 6;
    private static final int INITIAL_SCROLL_HEIGHT = 120;
    private static final int BUTTON_WIDTH = 100;
    private static final int CONTENT_TOP_PADDING = 8;
    private static final int CONTENT_BOTTOM_PADDING = 4;

    private final Screen parent;
    private final HeaderAndFooterLayout layout;
    private final TabManager tabManager;

    private TabNavigationBar tabNavigationBar;
    private ScrollableLayout scrollArea;

    private boolean small;
    private boolean medium;
    private boolean large;
    private boolean industrial;
    private boolean netherSmall;
    private boolean netherMedium;

    public SimpleGunpowderConfigScreen(Screen parent) {
        super(Component.translatable("simplegunpowder.config.title"));
        this.parent = parent;
        this.layout = new HeaderAndFooterLayout(this);
        this.tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);
        readConfig();
    }

    private void readConfig() {
        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        this.small = config.isSmallCraftingEnabled();
        this.medium = config.isMediumCraftingEnabled();
        this.large = config.isLargeCraftingEnabled();
        this.industrial = config.isIndustrialCraftingEnabled();
        this.netherSmall = config.isNetherSmallRecipeEnabled();
        this.netherMedium = config.isNetherMediumRecipeEnabled();
    }

    @Override
    protected void init() {
        this.scrollArea = new ScrollableLayout(this.minecraft, buildContentGrid(), INITIAL_SCROLL_HEIGHT);

        this.tabNavigationBar = TabNavigationBar.builder(this.tabManager, this.width)
            .addTabs(new ConfigTab(Component.translatable("simplegunpowder.config.tab.general"), this.scrollArea))
            .build();
        this.addRenderableWidget(this.tabNavigationBar);

        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(Component.translatable("simplegunpowder.config.apply"), button -> apply())
            .width(BUTTON_WIDTH)
            .build());
        footer.addChild(Button.builder(CommonComponents.GUI_CANCEL, button -> onClose())
            .width(BUTTON_WIDTH)
            .build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.tabNavigationBar.selectTab(0, false);
        this.repositionElements();
    }

    private GridLayout buildContentGrid() {
        GridLayout grid = new GridLayout();
        grid.columnSpacing(4).rowSpacing(ROW_SPACING);

        GridLayout.RowHelper rows = grid.createRowHelper(2);
        addSetting(rows, "simplegunpowder.config.small", this.small, value -> this.small = value);
        addSetting(rows, "simplegunpowder.config.medium", this.medium, value -> this.medium = value);
        addSetting(rows, "simplegunpowder.config.large", this.large, value -> this.large = value);
        addSetting(rows, "simplegunpowder.config.industrial", this.industrial, value -> this.industrial = value);
        addSetting(rows, "simplegunpowder.config.nether_small", this.netherSmall, value -> this.netherSmall = value);
        addSetting(rows, "simplegunpowder.config.nether_medium", this.netherMedium, value -> this.netherMedium = value);

        return grid;
    }

    private void addSetting(GridLayout.RowHelper rows, String translationKey, boolean initialValue, Consumer<Boolean> setter) {
        rows.addChild(new ReloadIcon(Component.translatable("simplegunpowder.config.reload_hint")));
        rows.addChild(toggle(translationKey, initialValue, setter));
    }

    private CycleButton<Boolean> toggle(String translationKey, boolean initialValue, Consumer<Boolean> setter) {
        return CycleButton.onOffBuilder(initialValue)
            .create(0, 0, WIDGET_WIDTH, WIDGET_HEIGHT, Component.translatable(translationKey),
                (button, value) -> setter.accept(value));
    }

    private void apply() {
        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        config.setSmallCraftingEnabled(this.small);
        config.setMediumCraftingEnabled(this.medium);
        config.setLargeCraftingEnabled(this.large);
        config.setIndustrialCraftingEnabled(this.industrial);
        config.setNetherSmallRecipeEnabled(this.netherSmall);
        config.setNetherMediumRecipeEnabled(this.netherMedium);
        SimpleGunpowderConfig.save();
        onClose();
    }

    @Override
    protected void repositionElements() {
        if (this.tabNavigationBar == null) {
            return;
        }
        this.tabNavigationBar.updateWidth(this.width);
        int headerBottom = this.tabNavigationBar.getRectangle().bottom();
        ScreenRectangle tabArea = new ScreenRectangle(0, headerBottom, this.width,
            this.height - this.layout.getFooterHeight() - headerBottom);
        this.tabManager.setTabArea(tabArea);
        this.layout.setHeaderHeight(headerBottom);
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    @Override
    protected void extractMenuBackground(GuiGraphicsExtractor graphics) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, CreateWorldScreen.TAB_HEADER_BACKGROUND,
            0, 0, 0.0F, 0.0F, this.width, this.layout.getHeaderHeight(), 16, 16);
        this.extractMenuBackground(graphics, 0, this.layout.getHeaderHeight(), this.width, this.height);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        graphics.blit(RenderPipelines.GUI_TEXTURED, Screen.FOOTER_SEPARATOR,
            0, this.height - this.layout.getFooterHeight() - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
    }

    private static final class ConfigTab implements Tab {

        private final Component title;
        private final ScrollableLayout content;

        private ConfigTab(Component title, ScrollableLayout content) {
            this.title = title;
            this.content = content;
        }

        @Override
        public Component getTabTitle() {
            return this.title;
        }

        @Override
        public Component getTabExtraNarration() {
            return Component.empty();
        }

        @Override
        public void visitChildren(Consumer<AbstractWidget> consumer) {
            this.content.visitWidgets(consumer);
        }

        @Override
        public void doLayout(ScreenRectangle area) {
            this.content.arrangeElements();
            int viewportHeight = area.height() - CONTENT_TOP_PADDING - CONTENT_BOTTOM_PADDING;
            this.content.setMaxHeight(viewportHeight);
            this.content.setMinHeight(viewportHeight);
            this.content.arrangeElements();
            int centeredX = area.left() + (area.width() - this.content.getWidth()) / 2;
            this.content.setPosition(centeredX, area.top() + CONTENT_TOP_PADDING);
        }
    }
}

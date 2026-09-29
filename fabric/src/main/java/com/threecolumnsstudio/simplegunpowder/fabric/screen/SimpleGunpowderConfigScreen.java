package com.threecolumnsstudio.simplegunpowder.fabric.screen;

import java.util.function.Consumer;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.ScrollableLayoutWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public final class SimpleGunpowderConfigScreen extends Screen {

    private static final int WIDGET_WIDTH = 300;
    private static final int WIDGET_HEIGHT = 20;
    private static final int ROW_SPACING = 6;
    private static final int INITIAL_SCROLL_HEIGHT = 120;
    private static final int BUTTON_WIDTH = 100;
    private static final int CONTENT_TOP_PADDING = 8;
    private static final int CONTENT_BOTTOM_PADDING = 4;

    private final Screen parent;
    private final ThreePartsLayoutWidget layout;
    private final TabManager tabManager;

    private TabNavigationWidget tabNavigationBar;
    private ScrollableLayoutWidget scrollArea;

    private boolean small;
    private boolean medium;
    private boolean large;
    private boolean industrial;
    private boolean netherSmall;
    private boolean netherMedium;

    public SimpleGunpowderConfigScreen(Screen parent) {
        super(Text.translatable("simplegunpowder.config.title"));
        this.parent = parent;
        this.layout = new ThreePartsLayoutWidget(this);
        this.tabManager = new TabManager(this::addDrawableChild, this::remove);
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
        this.scrollArea = new ScrollableLayoutWidget(this.client, buildContentGrid(), INITIAL_SCROLL_HEIGHT);

        this.tabNavigationBar = TabNavigationWidget.builder(this.tabManager, this.width)
            .tabs(new ConfigTab(Text.translatable("simplegunpowder.config.tab.general"), this.scrollArea))
            .build();
        this.addDrawableChild(this.tabNavigationBar);

        DirectionalLayoutWidget footer = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        footer.add(ButtonWidget.builder(Text.translatable("simplegunpowder.config.apply"), button -> apply())
            .width(BUTTON_WIDTH)
            .build());
        footer.add(ButtonWidget.builder(ScreenTexts.CANCEL, button -> close())
            .width(BUTTON_WIDTH)
            .build());

        this.layout.forEachChild(this::addDrawableChild);
        this.tabNavigationBar.selectTab(0, false);
        this.refreshWidgetPositions();
    }

    private GridWidget buildContentGrid() {
        GridWidget grid = new GridWidget();
        grid.setColumnSpacing(4).setRowSpacing(ROW_SPACING);

        GridWidget.Adder rows = grid.createAdder(2);
        addSetting(rows, "simplegunpowder.config.small", this.small, value -> this.small = value);
        addSetting(rows, "simplegunpowder.config.medium", this.medium, value -> this.medium = value);
        addSetting(rows, "simplegunpowder.config.large", this.large, value -> this.large = value);
        addSetting(rows, "simplegunpowder.config.industrial", this.industrial, value -> this.industrial = value);
        addSetting(rows, "simplegunpowder.config.nether_small", this.netherSmall, value -> this.netherSmall = value);
        addSetting(rows, "simplegunpowder.config.nether_medium", this.netherMedium, value -> this.netherMedium = value);

        return grid;
    }

    private void addSetting(GridWidget.Adder rows, String translationKey, boolean initialValue, Consumer<Boolean> setter) {
        rows.add(new ReloadIcon(Text.translatable("simplegunpowder.config.reload_hint")));
        rows.add(toggle(translationKey, initialValue, setter));
    }

    private CyclingButtonWidget<Boolean> toggle(String translationKey, boolean initialValue, Consumer<Boolean> setter) {
        return CyclingButtonWidget.onOffBuilder(initialValue)
            .build(0, 0, WIDGET_WIDTH, WIDGET_HEIGHT, Text.translatable(translationKey),
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
        close();
    }

    @Override
    protected void refreshWidgetPositions() {
        if (this.tabNavigationBar == null) {
            return;
        }
        this.tabNavigationBar.setWidth(this.width);
        this.tabNavigationBar.init();
        int headerBottom = this.tabNavigationBar.getNavigationFocus().getBottom();
        ScreenRect tabArea = new ScreenRect(0, headerBottom, this.width,
            this.height - this.layout.getFooterHeight() - headerBottom);
        this.tabManager.setTabArea(tabArea);
        this.layout.setHeaderHeight(headerBottom);
        this.layout.refreshPositions();
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, CreateWorldScreen.TAB_HEADER_BACKGROUND_TEXTURE,
            0, 0, 0.0F, 0.0F, this.width, this.layout.getHeaderHeight(), 16, 16);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, Screen.FOOTER_SEPARATOR_TEXTURE,
            0, this.height - this.layout.getFooterHeight() - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
    }

    private static final class ConfigTab implements Tab {

        private final Text title;
        private final ScrollableLayoutWidget content;

        private ConfigTab(Text title, ScrollableLayoutWidget content) {
            this.title = title;
            this.content = content;
        }

        @Override
        public Text getTitle() {
            return this.title;
        }

        @Override
        public Text getNarratedHint() {
            return Text.empty();
        }

        @Override
        public void forEachChild(Consumer<ClickableWidget> consumer) {
            this.content.forEachChild(consumer);
        }

        @Override
        public void refreshGrid(ScreenRect area) {
            this.content.refreshPositions();
            int viewportHeight = area.height() - CONTENT_TOP_PADDING - CONTENT_BOTTOM_PADDING;
            this.content.setHeight(viewportHeight);
            this.content.refreshPositions();
            int centeredX = area.getLeft() + (area.width() - this.content.getWidth()) / 2;
            this.content.setX(centeredX);
            this.content.setY(area.getTop() + CONTENT_TOP_PADDING);
        }
    }
}

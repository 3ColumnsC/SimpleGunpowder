package com.threecolumnsstudio.simplegunpowder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleGunpowderConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "simplegunpowder.json";

    private static volatile SimpleGunpowderConfig INSTANCE;

    private boolean enableSmallCrafting = true;
    private boolean enableMediumCrafting = true;
    private boolean enableLargeCrafting = true;
    private boolean enableIndustrialCrafting = true;
    private boolean enableNetherSmallRecipe = true;
    private boolean enableNetherMediumRecipe = true;

    public boolean isSmallCraftingEnabled() {
        return enableSmallCrafting;
    }

    public boolean isMediumCraftingEnabled() {
        return enableMediumCrafting;
    }

    public boolean isLargeCraftingEnabled() {
        return enableLargeCrafting;
    }

    public boolean isIndustrialCraftingEnabled() {
        return enableIndustrialCrafting;
    }

    public boolean isNetherSmallRecipeEnabled() {
        return enableNetherSmallRecipe;
    }

    public boolean isNetherMediumRecipeEnabled() {
        return enableNetherMediumRecipe;
    }

    public void setSmallCraftingEnabled(boolean enabled) {
        this.enableSmallCrafting = enabled;
    }

    public void setMediumCraftingEnabled(boolean enabled) {
        this.enableMediumCrafting = enabled;
    }

    public void setLargeCraftingEnabled(boolean enabled) {
        this.enableLargeCrafting = enabled;
    }

    public void setIndustrialCraftingEnabled(boolean enabled) {
        this.enableIndustrialCrafting = enabled;
    }

    public void setNetherSmallRecipeEnabled(boolean enabled) {
        this.enableNetherSmallRecipe = enabled;
    }

    public void setNetherMediumRecipeEnabled(boolean enabled) {
        this.enableNetherMediumRecipe = enabled;
    }

    public boolean isEnabled(String recipePath) {
        RecipeToggle toggle = RecipeToggle.fromPath(recipePath);
        return toggle == null || toggle.isEnabled(this);
    }

    public static SimpleGunpowderConfig getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("SimpleGunpowderConfig accessed before load()");
        }
        return INSTANCE;
    }

    public static void load() {
        Path configPath = configPath();

        if (!Files.exists(configPath)) {
            INSTANCE = new SimpleGunpowderConfig();
            save();
            return;
        }

        try {
            SimpleGunpowderConfig loaded = GSON.fromJson(Files.readString(configPath), SimpleGunpowderConfig.class);
            if (loaded == null) {
                SimpleGunpowder.LOGGER.warn("Config file is empty, using defaults without overwriting it ({})",
                        configPath);
                INSTANCE = new SimpleGunpowderConfig();
            } else {
                INSTANCE = loaded;
            }
        } catch (Exception e) {
            SimpleGunpowder.LOGGER.warn(
                    "Could not read config, using defaults without overwriting it ({}, {})",
                    configPath, e.getClass().getSimpleName());
            INSTANCE = new SimpleGunpowderConfig();
        }
    }

    public static boolean save() {
        Path configPath = configPath();
        try {
            Path parent = configPath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                GSON.toJson(INSTANCE, writer);
            }
            return true;
        } catch (IOException | SecurityException e) {
            SimpleGunpowder.LOGGER.error("Could not save config to {} ({})",
                    configPath, e.getClass().getSimpleName());
            return false;
        }
    }

    private static Path configPath() {
        return Platform.get().getConfigDir().resolve(FILE_NAME);
    }
}
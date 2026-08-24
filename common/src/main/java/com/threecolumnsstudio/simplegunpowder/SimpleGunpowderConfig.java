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
    private boolean enableRefinedSulfurRecipe = true;
    private boolean enablePotentSulfurRecipe = true;
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

    public boolean isRefinedSulfurRecipeEnabled() {
        return enableRefinedSulfurRecipe;
    }

    public boolean isPotentSulfurRecipeEnabled() {
        return enablePotentSulfurRecipe;
    }

    public boolean isNetherSmallRecipeEnabled() {
        return enableNetherSmallRecipe;
    }

    public boolean isNetherMediumRecipeEnabled() {
        return enableNetherMediumRecipe;
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

        String saved = null;
        try {
            saved = Files.exists(configPath) ? Files.readString(configPath) : null;
            INSTANCE = saved == null ? new SimpleGunpowderConfig()
                    : GSON.fromJson(saved, SimpleGunpowderConfig.class);
            if (INSTANCE == null) {
                INSTANCE = new SimpleGunpowderConfig();
            }
        } catch (Exception e) {
            SimpleGunpowder.LOGGER.warn("Could not read config, using defaults", e);
            INSTANCE = new SimpleGunpowderConfig();
        }

        if (!GSON.toJson(INSTANCE).equals(saved)) {
            save();
        }
    }

    public static void save() {
        try (Writer writer = Files.newBufferedWriter(configPath())) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            SimpleGunpowder.LOGGER.error("Could not save config", e);
        }
    }

    private static Path configPath() {
        return Platform.get().getConfigDir().resolve(FILE_NAME);
    }
}
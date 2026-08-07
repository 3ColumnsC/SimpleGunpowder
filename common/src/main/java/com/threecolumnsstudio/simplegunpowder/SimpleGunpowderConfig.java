package com.threecolumnsstudio.simplegunpowder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleGunpowderConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static volatile SimpleGunpowderConfig INSTANCE;

    public boolean enableSmallCrafting = true;
    public boolean enableMediumCrafting = true;
    public boolean enableLargeCrafting = true;
    public boolean enableIndustrialCrafting = true;
    public boolean enableRefinedSulfurRecipe = true;
    public boolean enablePotentSulfurRecipe = true;
    public boolean enableNetherSmallRecipe = true;
    public boolean enableNetherMediumRecipe = true;

    public boolean isEnabled(String recipePath) {
        return switch (recipePath) {
            case "small_gunpowder" -> enableSmallCrafting;
            case "medium_gunpowder" -> enableMediumCrafting;
            case "large_gunpowder" -> enableLargeCrafting;
            case "industrial_gunpowder" -> enableIndustrialCrafting;
            case "refined_sulfur_gunpowder" -> enableRefinedSulfurRecipe;
            case "potent_sulfur_gunpowder" -> enablePotentSulfurRecipe;
            case "nether_small_gunpowder" -> enableNetherSmallRecipe;
            case "nether_medium_gunpowder" -> enableNetherMediumRecipe;
            default -> true;
        };
    }

    public static SimpleGunpowderConfig getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("SimpleGunpowderConfig accessed before load()");
        }
        return INSTANCE;
    }

    public static void load() {
        Path configPath = Platform.get().getConfigDir().resolve("simplegunpowder.json");

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
        Path configPath = Platform.get().getConfigDir().resolve("simplegunpowder.json");
        try (Writer writer = Files.newBufferedWriter(configPath)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            SimpleGunpowder.LOGGER.error("Could not save config", e);
        }
    }
}

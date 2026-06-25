package com.threecolumnsstudio.simplegunpowder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleGunpowderConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static SimpleGunpowderConfig INSTANCE;

    public boolean enableSmallCrafting = true;
    public boolean enableMediumCrafting = true;
    public boolean enableLargeCrafting = true;
    public boolean enableIndustrialCrafting = true;

    public static SimpleGunpowderConfig getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("SimpleGunpowderConfig accessed before load()");
        }
        return INSTANCE;
    }

    public static void load() {
        // Platform.get() resolves to FabricLoader or FMLPaths depending on loader
        Path configPath = Platform.get().getConfigDir().resolve("simplegunpowder.json");

        if (Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                INSTANCE = GSON.fromJson(reader, SimpleGunpowderConfig.class);
                if (INSTANCE == null) INSTANCE = new SimpleGunpowderConfig();
                validate();
                save();
            } catch (Exception e) {
                SimpleGunpowder.LOGGER.warn("Could not read config, using defaults", e);
                INSTANCE = new SimpleGunpowderConfig();
                save();
            }
        } else {
            INSTANCE = new SimpleGunpowderConfig();
            save();
        }
    }

    private static void validate() {
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

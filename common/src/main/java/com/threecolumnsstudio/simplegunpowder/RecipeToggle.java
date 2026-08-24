package com.threecolumnsstudio.simplegunpowder;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum RecipeToggle {

    SMALL_CRAFTING("small_gunpowder", SimpleGunpowderConfig::isSmallCraftingEnabled),
    MEDIUM_CRAFTING("medium_gunpowder", SimpleGunpowderConfig::isMediumCraftingEnabled),
    LARGE_CRAFTING("large_gunpowder", SimpleGunpowderConfig::isLargeCraftingEnabled),
    INDUSTRIAL_CRAFTING("industrial_gunpowder", SimpleGunpowderConfig::isIndustrialCraftingEnabled),
    NETHER_SMALL("nether_small_gunpowder", SimpleGunpowderConfig::isNetherSmallRecipeEnabled),
    NETHER_MEDIUM("nether_medium_gunpowder", SimpleGunpowderConfig::isNetherMediumRecipeEnabled);

    private static final Map<String, RecipeToggle> BY_PATH = Stream.of(values())
            .collect(Collectors.toUnmodifiableMap(RecipeToggle::path, Function.identity()));

    private final String path;
    private final Predicate<SimpleGunpowderConfig> enabled;

    RecipeToggle(String path, Predicate<SimpleGunpowderConfig> enabled) {
        this.path = path;
        this.enabled = enabled;
    }

    String path() {
        return path;
    }

    static RecipeToggle fromPath(String path) {
        return BY_PATH.get(path);
    }

    boolean isEnabled(SimpleGunpowderConfig config) {
        return enabled.test(config);
    }
}
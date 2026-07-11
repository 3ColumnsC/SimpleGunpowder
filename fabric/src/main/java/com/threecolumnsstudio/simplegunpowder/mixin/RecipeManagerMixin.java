package com.threecolumnsstudio.simplegunpowder.mixin;

import com.google.gson.JsonElement;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Shadow
    private Map<Identifier, Recipe<?>> recipesById;

    @Shadow
    private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;

    @Inject(method = "apply", at = @At("TAIL"))
    private void onApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager,
                         Profiler profiler, CallbackInfo ci) {

        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();

        SimpleGunpowder.LOGGER.info("RecipeManagerMixin: {} recipes loaded, config small={} medium={} large={} industrial={}",
            this.recipesById.size(),
            config.enableSmallCrafting, config.enableMediumCrafting,
            config.enableLargeCrafting, config.enableIndustrialCrafting);

        if (config.enableSmallCrafting && config.enableMediumCrafting &&
            config.enableLargeCrafting && config.enableIndustrialCrafting &&
            config.enableNetherSmallRecipe && config.enableNetherMediumRecipe) {
            SimpleGunpowder.LOGGER.info("RecipeManagerMixin: all recipes enabled, skipping filter");
            return;
        }

        boolean modified = false;
        Map<Identifier, Recipe<?>> filtered = new HashMap<>();

        for (Map.Entry<Identifier, Recipe<?>> entry : this.recipesById.entrySet()) {
            Identifier id = entry.getKey();

            if (id.getNamespace().equals(SimpleGunpowder.MOD_ID)) {
                SimpleGunpowder.LOGGER.info("Found custom recipe: {}", id);

                if (id.getPath().equals("small_gunpowder") && !config.enableSmallCrafting) {
                    SimpleGunpowder.LOGGER.info("Disabled small_gunpowder recipe");
                    modified = true;
                    continue;
                }
                if (id.getPath().equals("medium_gunpowder") && !config.enableMediumCrafting) {
                    SimpleGunpowder.LOGGER.info("Disabled medium_gunpowder recipe");
                    modified = true;
                    continue;
                }
                if (id.getPath().equals("large_gunpowder") && !config.enableLargeCrafting) {
                    SimpleGunpowder.LOGGER.info("Disabled large_gunpowder recipe");
                    modified = true;
                    continue;
                }
                if (id.getPath().equals("industrial_gunpowder") && !config.enableIndustrialCrafting) {
                    SimpleGunpowder.LOGGER.info("Disabled industrial_gunpowder recipe");
                    modified = true;
                    continue;
                }
                if (id.getPath().equals("nether_small_gunpowder") && !config.enableNetherSmallRecipe) {
                    SimpleGunpowder.LOGGER.info("Disabled nether_small_gunpowder recipe");
                    modified = true;
                    continue;
                }
                if (id.getPath().equals("nether_medium_gunpowder") && !config.enableNetherMediumRecipe) {
                    SimpleGunpowder.LOGGER.info("Disabled nether_medium_gunpowder recipe");
                    modified = true;
                    continue;
                }
            }
            filtered.put(entry.getKey(), entry.getValue());
        }

        if (modified) {
            SimpleGunpowder.LOGGER.info("RecipeManagerMixin: filtered to {} recipes", filtered.size());
            this.recipesById = filtered;

            Map<RecipeType<?>, Map<Identifier, Recipe<?>>> filteredByType = new HashMap<>();
            for (Map.Entry<Identifier, Recipe<?>> entry : filtered.entrySet()) {
                filteredByType.computeIfAbsent(entry.getValue().getType(), k -> new HashMap<>())
                    .put(entry.getKey(), entry.getValue());
            }
            this.recipes = filteredByType;
        } else {
            SimpleGunpowder.LOGGER.info("RecipeManagerMixin: no recipes filtered, {} recipes remain", this.recipesById.size());
        }
    }
}

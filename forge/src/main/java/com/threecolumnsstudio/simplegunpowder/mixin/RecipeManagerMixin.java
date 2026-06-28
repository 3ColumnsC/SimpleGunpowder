package com.threecolumnsstudio.simplegunpowder.mixin;

import com.google.gson.JsonElement;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Shadow
    private Map<ResourceLocation, Recipe<?>> byName;

    @Shadow
    private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

    @Inject(method = "apply", at = @At("TAIL"))
    private void onApply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager,
                         ProfilerFiller profilerFiller, CallbackInfo ci) {

        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();

        SimpleGunpowder.LOGGER.info("RecipeManagerMixin: {} recipes loaded, config small={} medium={} large={} industrial={}",
            this.byName.size(),
            config.enableSmallCrafting, config.enableMediumCrafting,
            config.enableLargeCrafting, config.enableIndustrialCrafting);

        if (config.enableSmallCrafting && config.enableMediumCrafting &&
            config.enableLargeCrafting && config.enableIndustrialCrafting) {
            SimpleGunpowder.LOGGER.info("RecipeManagerMixin: all recipes enabled, skipping filter");
            return;
        }

        boolean modified = false;
        Map<ResourceLocation, Recipe<?>> filtered = new HashMap<>();

        for (Map.Entry<ResourceLocation, Recipe<?>> entry : this.byName.entrySet()) {
            ResourceLocation id = entry.getKey();

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
            }
            filtered.put(entry.getKey(), entry.getValue());
        }

        if (modified) {
            SimpleGunpowder.LOGGER.info("RecipeManagerMixin: filtered to {} recipes", filtered.size());
            this.byName = filtered;

            Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> filteredByType = new HashMap<>();
            for (Map.Entry<ResourceLocation, Recipe<?>> entry : filtered.entrySet()) {
                filteredByType.computeIfAbsent(entry.getValue().getType(), k -> new HashMap<>())
                    .put(entry.getKey(), entry.getValue());
            }
            this.recipes = filteredByType;
        } else {
            SimpleGunpowder.LOGGER.info("RecipeManagerMixin: no recipes filtered, {} recipes remain", this.byName.size());
        }
    }
}

package com.threecolumnsstudio.simplegunpowder.mixin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.recipe.RecipeEntry;
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
    private Map<Identifier, RecipeEntry<?>> recipesById;

    @Shadow
    private Multimap<RecipeType<?>, RecipeEntry<?>> recipesByType;

    @Inject(method = "apply", at = @At("TAIL"))
    private void onApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager,
                         Profiler profiler, CallbackInfo ci) {

        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        boolean modified = false;
        Map<Identifier, RecipeEntry<?>> filtered = new HashMap<>();

        for (Map.Entry<Identifier, RecipeEntry<?>> entry : this.recipesById.entrySet()) {
            Identifier id = entry.getKey();

            if (id.getNamespace().equals(SimpleGunpowder.MOD_ID)) {
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
            }
            filtered.put(entry.getKey(), entry.getValue());
        }

        if (modified) {
            this.recipesById = filtered;

            Multimap<RecipeType<?>, RecipeEntry<?>> filteredByType = ArrayListMultimap.create();
            for (RecipeEntry<?> recipe : filtered.values()) {
                filteredByType.put(recipe.value().getType(), recipe);
            }
            this.recipesByType = filteredByType;
        }
    }
}

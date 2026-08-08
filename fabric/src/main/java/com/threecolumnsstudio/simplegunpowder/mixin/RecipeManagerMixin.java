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
        boolean modified = false;
        Map<Identifier, Recipe<?>> filtered = new HashMap<>();

        for (Map.Entry<Identifier, Recipe<?>> entry : this.recipesById.entrySet()) {
            Identifier id = entry.getKey();
            if (id.getNamespace().equals(SimpleGunpowder.MOD_ID)
                    && !config.isEnabled(id.getPath())) {
                SimpleGunpowder.LOGGER.info(
                    "Disabled {} recipe (re-enable it in config/simplegunpowder.json)", id.getPath());
                modified = true;
                continue;
            }
            filtered.put(entry.getKey(), entry.getValue());
        }

        if (modified) {
            this.recipesById = filtered;

            Map<RecipeType<?>, Map<Identifier, Recipe<?>>> filteredByType = new HashMap<>();
            for (Map.Entry<Identifier, Recipe<?>> entry : filtered.entrySet()) {
                filteredByType.computeIfAbsent(entry.getValue().getType(), k -> new HashMap<>())
                    .put(entry.getKey(), entry.getValue());
            }
            this.recipes = filteredByType;
        }
    }
}
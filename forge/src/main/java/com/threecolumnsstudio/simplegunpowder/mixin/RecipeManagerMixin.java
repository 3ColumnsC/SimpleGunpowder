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
        boolean modified = false;
        Map<ResourceLocation, Recipe<?>> filtered = new HashMap<>();

        for (Map.Entry<ResourceLocation, Recipe<?>> entry : this.byName.entrySet()) {
            ResourceLocation id = entry.getKey();
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
            this.byName = filtered;

            Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> filteredByType = new HashMap<>();
            for (Map.Entry<ResourceLocation, Recipe<?>> entry : filtered.entrySet()) {
                filteredByType.computeIfAbsent(entry.getValue().getType(), k -> new HashMap<>())
                    .put(entry.getKey(), entry.getValue());
            }
            this.recipes = filteredByType;
        }
    }
}
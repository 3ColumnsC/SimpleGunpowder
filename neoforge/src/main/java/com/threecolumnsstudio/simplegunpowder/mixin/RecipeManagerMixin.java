package com.threecolumnsstudio.simplegunpowder.mixin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Shadow
    private Map<ResourceLocation, RecipeHolder<?>> byName;

    @Shadow
    private Multimap<RecipeType<?>, RecipeHolder<?>> byType;

    @Inject(method = "apply", at = @At("TAIL"))
    private void onApply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager,
                         ProfilerFiller profilerFiller, CallbackInfo ci) {
        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        List<String> disabled = new ArrayList<>();
        Map<ResourceLocation, RecipeHolder<?>> filtered = new HashMap<>();

        for (Map.Entry<ResourceLocation, RecipeHolder<?>> entry : this.byName.entrySet()) {
            ResourceLocation id = entry.getKey();
            if (id.getNamespace().equals(SimpleGunpowder.MOD_ID)
                    && !config.isEnabled(id.getPath())) {
                disabled.add(id.getPath());
                continue;
            }
            filtered.put(entry.getKey(), entry.getValue());
        }

        if (!disabled.isEmpty()) {
            SimpleGunpowder.LOGGER.info(
                "Disabled {} recipe(s): {}", disabled.size(), String.join(", ", disabled));
            this.byName = filtered;

            Multimap<RecipeType<?>, RecipeHolder<?>> filteredByType = ArrayListMultimap.create();
            for (RecipeHolder<?> recipe : filtered.values()) {
                filteredByType.put(recipe.value().getType(), recipe);
            }
            this.byType = filteredByType;
        }
    }
}
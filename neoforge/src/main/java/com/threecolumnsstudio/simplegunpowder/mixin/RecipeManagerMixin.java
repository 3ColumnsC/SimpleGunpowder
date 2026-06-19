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

import java.util.HashMap;
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
        boolean modified = false;
        Map<ResourceLocation, RecipeHolder<?>> filtered = new HashMap<>();

        for (Map.Entry<ResourceLocation, RecipeHolder<?>> entry : this.byName.entrySet()) {
            ResourceLocation id = entry.getKey();

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
            this.byName = filtered;

            Multimap<RecipeType<?>, RecipeHolder<?>> filteredByType = ArrayListMultimap.create();
            for (RecipeHolder<?> recipe : filtered.values()) {
                filteredByType.put(recipe.value().getType(), recipe);
            }
            this.byType = filteredByType;
        }
    }
}

package com.threecolumnsstudio.simplegunpowder.mixin;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Shadow
    private RecipeMap recipes;

    @Inject(method = "apply", at = @At("TAIL"))
    private void onApply(RecipeMap recipeMap, ResourceManager resourceManager,
                         ProfilerFiller profilerFiller, CallbackInfo ci) {
        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        List<String> disabled = new ArrayList<>();
        List<RecipeHolder<?>> filtered = new ArrayList<>();

        for (RecipeHolder<?> holder : this.recipes.values()) {
            Identifier id = holder.id().identifier();
            if (id.getNamespace().equals(SimpleGunpowder.MOD_ID)
                    && !config.isEnabled(id.getPath())) {
                disabled.add(id.getPath());
                continue;
            }
            filtered.add(holder);
        }

        if (!disabled.isEmpty()) {
            SimpleGunpowder.LOGGER.info(
                "Disabled {} recipe(s): {}", disabled.size(), String.join(", ", disabled));
            this.recipes = RecipeMap.create(filtered);
        }
    }
}
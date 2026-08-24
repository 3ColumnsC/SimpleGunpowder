package com.threecolumnsstudio.simplegunpowder.mixin;

import com.threecolumnsstudio.simplegunpowder.RecipeFilter;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Shadow
    private RecipeMap recipes;

    @Inject(method = "apply", at = @At("TAIL"))
    private void onApply(RecipeMap recipeMap, ResourceManager resourceManager,
                         ProfilerFiller profilerFiller, CallbackInfo ci) {
        this.recipes = RecipeFilter.filter(this.recipes);
    }
}
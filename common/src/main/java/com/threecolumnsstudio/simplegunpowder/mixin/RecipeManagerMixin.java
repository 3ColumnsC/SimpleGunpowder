package com.threecolumnsstudio.simplegunpowder.mixin;

import com.threecolumnsstudio.simplegunpowder.RecipeFilter;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/RecipeMap;create(Lnet/minecraft/core/HolderLookup;)Lnet/minecraft/world/item/crafting/RecipeMap;"),
            index = 0)
    private HolderLookup<Recipe<?>> filterDisabledRecipes(HolderLookup<Recipe<?>> recipes) {
        return RecipeFilter.filter(recipes);
    }
}

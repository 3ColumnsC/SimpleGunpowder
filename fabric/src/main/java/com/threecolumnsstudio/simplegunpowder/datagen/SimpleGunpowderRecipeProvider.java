package com.threecolumnsstudio.simplegunpowder.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class SimpleGunpowderRecipeProvider extends FabricRecipeProvider {

    public SimpleGunpowderRecipeProvider(FabricPackOutput output,
                                          CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public String getName() {
        return "SimpleGunpowderRecipeProvider";
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider,
                                                   RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.MISC, Items.GUNPOWDER, 4)
                    .pattern("###")
                    .define('#', Items.COAL)
                    .unlockedBy("has_coal", has(Items.COAL))
                    .save(recipeOutput, "simplegunpowder:small_gunpowder");

                shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 6)
                    .requires(Items.COAL, 2)
                    .requires(Items.SAND, 2)
                    .unlockedBy("has_coal", has(Items.COAL))
                    .save(recipeOutput, "simplegunpowder:medium_gunpowder");

                shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 10)
                    .requires(Items.COAL, 2)
                    .requires(Items.SAND, 2)
                    .requires(Items.REDSTONE, 2)
                    .unlockedBy("has_coal", has(Items.COAL))
                    .save(recipeOutput, "simplegunpowder:large_gunpowder");

                shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 32)
                    .requires(Items.COAL_BLOCK)
                    .requires(Items.SAND)
                    .requires(Items.REDSTONE)
                    .unlockedBy("has_coal_block", has(Items.COAL_BLOCK))
                    .save(recipeOutput, "simplegunpowder:industrial_gunpowder");
            }
        };
    }
}

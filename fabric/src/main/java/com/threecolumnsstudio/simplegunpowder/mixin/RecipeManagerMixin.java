package com.threecolumnsstudio.simplegunpowder.mixin;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.recipe.PreparedRecipes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerRecipeManager.class)
public abstract class RecipeManagerMixin {

    @Shadow
    private PreparedRecipes preparedRecipes;

    @Inject(method = "apply", at = @At("TAIL"))
    private void onApply(PreparedRecipes preparedRecipes, ResourceManager resourceManager,
                         Profiler profiler, CallbackInfo ci) {

        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        boolean modified = false;
        List<RecipeEntry<?>> filtered = new ArrayList<>();

        for (RecipeEntry<?> entry : this.preparedRecipes.recipes()) {
            Identifier id = entry.id().getValue();

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
            filtered.add(entry);
        }

        if (modified) {
            this.preparedRecipes = PreparedRecipes.of(filtered);
        }
    }
}

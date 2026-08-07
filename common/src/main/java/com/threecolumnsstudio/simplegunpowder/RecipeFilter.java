package com.threecolumnsstudio.simplegunpowder;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Removes simplegunpowder recipes that are disabled in the config.
 * Called from the per-loader RecipeManagerMixin on every datapack reload.
 */
public final class RecipeFilter {

    private RecipeFilter() {}

    public static RecipeMap filter(RecipeMap recipes) {
        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        boolean modified = false;
        List<RecipeHolder<?>> filtered = new ArrayList<>();

        for (RecipeHolder<?> holder : recipes.values()) {
            Identifier id = holder.id().identifier();
            if (id.getNamespace().equals(SimpleGunpowder.MOD_ID)
                    && !config.isEnabled(id.getPath())) {
                SimpleGunpowder.LOGGER.info(
                    "Disabled {} recipe (re-enable it in config/simplegunpowder.json)", id.getPath());
                modified = true;
                continue;
            }
            filtered.add(holder);
        }

        return modified ? RecipeMap.create(filtered) : recipes;
    }
}

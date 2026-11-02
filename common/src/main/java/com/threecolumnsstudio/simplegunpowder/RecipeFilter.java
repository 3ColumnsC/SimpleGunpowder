package com.threecolumnsstudio.simplegunpowder;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;

import java.util.ArrayList;
import java.util.List;

public final class RecipeFilter {

    private RecipeFilter() {}

    public static RecipeMap filter(RecipeMap recipes) {
        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        List<String> disabled = new ArrayList<>();
        List<RecipeHolder<?>> filtered = new ArrayList<>();

        for (RecipeHolder<?> holder : recipes.values()) {
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
        }
        return disabled.isEmpty() ? recipes : RecipeMap.create(filtered);
    }
}

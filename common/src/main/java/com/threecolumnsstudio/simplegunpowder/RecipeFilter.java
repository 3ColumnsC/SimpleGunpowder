package com.threecolumnsstudio.simplegunpowder;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class RecipeFilter {

    private RecipeFilter() {}

    public static HolderLookup<Recipe<?>> filter(HolderLookup<Recipe<?>> recipes) {
        List<String> disabled = recipes.listElements()
                .map(reference -> reference.key())
                .filter(RecipeFilter::isDisabled)
                .map(key -> key.identifier().getPath())
                .toList();
        if (!disabled.isEmpty()) {
            SimpleGunpowder.LOGGER.info(
                    "Disabled {} recipe(s): {}",
                    disabled.size(), String.join(", ", disabled));
        }
        return new DisabledRecipeLookup(recipes);
    }

    private static boolean isDisabled(ResourceKey<Recipe<?>> key) {
        Identifier id = key.identifier();
        return id.getNamespace().equals(SimpleGunpowder.MOD_ID)
                && !SimpleGunpowderConfig.getInstance().isEnabled(id.getPath());
    }

    private record DisabledRecipeLookup(HolderLookup<Recipe<?>> delegate)
            implements HolderLookup<Recipe<?>> {

        @Override
        public Stream<Holder.Reference<Recipe<?>>> listElements() {
            return delegate.listElements().filter(reference -> !isDisabled(reference.key()));
        }

        @Override
        public Stream<HolderSet.Named<Recipe<?>>> listTags() {
            return delegate.listTags();
        }

        @Override
        public Optional<Holder.Reference<Recipe<?>>> get(ResourceKey<Recipe<?>> key) {
            return delegate.get(key);
        }

        @Override
        public Optional<HolderSet.Named<Recipe<?>>> get(TagKey<Recipe<?>> tag) {
            return delegate.get(tag);
        }

        @Override
        public boolean canSerialize(HolderOwner<Recipe<?>> owner) {
            return delegate.canSerialize(owner);
        }
    }
}

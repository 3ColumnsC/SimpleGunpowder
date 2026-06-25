package com.threecolumnsstudio.simplegunpowder.fabric;

import com.threecolumnsstudio.simplegunpowder.Platform;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import java.util.Arrays;
import java.util.Optional;

public class SimpleGunpowderFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Platform.set(new FabricPlatform());
        SimpleGunpowder.init();
        registerTrades();
    }

    private void registerTrades() {
        addTrade(VillagerProfession.CLERIC, 1);
        addTrade(VillagerProfession.FLETCHER, 1);
    }

    private void addTrade(RegistryKey<VillagerProfession> profession, int level) {
        TradeOffers.Factory factory = (world, entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2), Optional.empty(),
                new ItemStack(Items.GUNPOWDER, 10), 16, 2, 0.05F
        );
        Int2ObjectMap<TradeOffers.Factory[]> leveled = TradeOffers.PROFESSION_TO_LEVELED_TRADE
                .computeIfAbsent(profession, k -> new Int2ObjectOpenHashMap<>());
        TradeOffers.Factory[] existing = leveled.get(level);
        TradeOffers.Factory[] combined;
        if (existing == null) {
            combined = new TradeOffers.Factory[]{ factory };
        } else {
            combined = Arrays.copyOf(existing, existing.length + 1);
            combined[existing.length] = factory;
        }
        leveled.put(level, combined);
    }
}

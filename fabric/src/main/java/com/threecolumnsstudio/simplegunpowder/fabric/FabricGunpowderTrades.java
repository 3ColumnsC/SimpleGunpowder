package com.threecolumnsstudio.simplegunpowder.fabric;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;

import java.util.Optional;

public final class FabricGunpowderTrades {

    private FabricGunpowderTrades() {}

    public static void maybeAddGunpowderTrade(VillagerEntity villager) {
        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        if (!config.enableTrades) return;

        VillagerData data = villager.getVillagerData();
        if (data.level() != 1) return;

        RegistryEntry<VillagerProfession> profession = data.profession();
        boolean isFletcherOrCleric =
                profession.matchesId(Identifier.ofVanilla("fletcher")) ||
                        profession.matchesId(Identifier.ofVanilla("cleric"));
        if (!isFletcherOrCleric) return;

        TradeOfferList offers = villager.getOffers();

        for (TradeOffer offer : offers) {
            ItemStack result = offer.getSellItem();
            if (result.isOf(Items.GUNPOWDER) && result.getCount() == config.tradeGunpowderAmount) {
                return;
            }
        }

        offers.add(new TradeOffer(
                new TradedItem(Items.EMERALD, config.tradeEmeraldCost),
                Optional.empty(),
                new ItemStack(Items.GUNPOWDER, config.tradeGunpowderAmount),
                16,
                2,
                0.05F
        ));
    }
}

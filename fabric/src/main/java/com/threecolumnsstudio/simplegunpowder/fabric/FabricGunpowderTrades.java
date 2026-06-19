package com.threecolumnsstudio.simplegunpowder.fabric;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
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
        if (data.getLevel() != 1) return;

        VillagerProfession profession = data.getProfession();
        boolean isFletcherOrCleric =
                Registries.VILLAGER_PROFESSION.getId(profession).equals(Identifier.of("minecraft", "fletcher")) ||
                        Registries.VILLAGER_PROFESSION.getId(profession).equals(Identifier.of("minecraft", "cleric"));
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

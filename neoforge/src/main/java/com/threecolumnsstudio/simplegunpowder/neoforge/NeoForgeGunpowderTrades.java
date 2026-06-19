package com.threecolumnsstudio.simplegunpowder.neoforge;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Optional;

public final class NeoForgeGunpowderTrades {

    private NeoForgeGunpowderTrades() {}

    public static void maybeAddGunpowderTrade(Villager villager) {
        SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
        if (!config.enableTrades) return;

        VillagerData data = villager.getVillagerData();
        if (data.getLevel() != 1) return;

        VillagerProfession profession = data.getProfession();
        boolean isFletcherOrCleric =
                BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession).equals(ResourceLocation.withDefaultNamespace("fletcher")) ||
                        BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession).equals(ResourceLocation.withDefaultNamespace("cleric"));
        if (!isFletcherOrCleric) return;

        var offers = villager.getOffers();

        for (MerchantOffer offer : offers) {
            ItemStack result = offer.getResult();
            if (result.is(Items.GUNPOWDER) && result.getCount() == config.tradeGunpowderAmount) {
                return;
            }
        }

        offers.add(new MerchantOffer(
                new ItemCost(Items.EMERALD, config.tradeEmeraldCost),
                Optional.empty(),
                new ItemStack(Items.GUNPOWDER, config.tradeGunpowderAmount),
                16,
                2,
                0.05F
        ));
    }
}

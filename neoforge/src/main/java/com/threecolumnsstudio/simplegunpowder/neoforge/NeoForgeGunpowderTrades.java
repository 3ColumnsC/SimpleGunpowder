package com.threecolumnsstudio.simplegunpowder.neoforge;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderConfig;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
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
        if (data.level() != 1) return;

        Holder<VillagerProfession> profession = data.profession();
        boolean isFletcherOrCleric =
                profession.is(Identifier.withDefaultNamespace("fletcher")) ||
                        profession.is(Identifier.withDefaultNamespace("cleric"));
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

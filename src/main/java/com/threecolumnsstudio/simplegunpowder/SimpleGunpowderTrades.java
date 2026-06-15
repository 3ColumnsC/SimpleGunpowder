package com.threecolumnsstudio.simplegunpowder;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public final class SimpleGunpowderTrades {
	private SimpleGunpowderTrades() {
	}

	public static void logConfiguration() {
		SimpleGunpowder.LOGGER.info("Gunpowder trades are {}", SimpleGunpowderConfig.getInstance().enableTrades ? "enabled" : "disabled");
	}

	public static void maybeAddGunpowderTrade(Villager villager) {
		SimpleGunpowderConfig config = SimpleGunpowderConfig.getInstance();
		if (!config.enableTrades) return;

		VillagerData data = villager.getVillagerData();
		if (data.level() != 1) return;

		Holder<VillagerProfession> profession = data.profession();
		if (!profession.is(VillagerProfession.FLETCHER) && !profession.is(VillagerProfession.CLERIC)) return;

		MerchantOffers offers = villager.getOffers();

		for (MerchantOffer offer : offers) {
			ItemStack result = offer.getResult();
			if (result.is(Items.GUNPOWDER) && result.getCount() == config.tradeGunpowderAmount) {
				return;
			}
		}

		offers.add(new MerchantOffer(
			new ItemCost(Items.EMERALD, config.tradeEmeraldCost),
			new ItemStack(Items.GUNPOWDER, config.tradeGunpowderAmount),
			16,
			2,
			0.05F
		));
	}
}

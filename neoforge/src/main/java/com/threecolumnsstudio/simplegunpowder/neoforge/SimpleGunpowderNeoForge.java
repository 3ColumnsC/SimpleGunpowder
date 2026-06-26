package com.threecolumnsstudio.simplegunpowder.neoforge;

import com.threecolumnsstudio.simplegunpowder.Platform;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import java.util.Optional;

@Mod(SimpleGunpowder.MOD_ID)
public class SimpleGunpowderNeoForge {

    public SimpleGunpowderNeoForge() {
        Platform.set(new NeoforgePlatform());
        SimpleGunpowder.init();
        NeoForge.EVENT_BUS.addListener(this::onVillagerTrades);
    }

    private void onVillagerTrades(VillagerTradesEvent event) {
        var type = event.getType();
        var key = BuiltInRegistries.VILLAGER_PROFESSION.getKey(type);
        if (!key.equals(ResourceLocation.withDefaultNamespace("cleric")) &&
                !key.equals(ResourceLocation.withDefaultNamespace("fletcher"))) return;
        event.getTrades().get(1).add((entity, random) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 2), Optional.empty(),
                new ItemStack(Items.GUNPOWDER, 10), 16, 2, 0.05F
        ));
    }
}

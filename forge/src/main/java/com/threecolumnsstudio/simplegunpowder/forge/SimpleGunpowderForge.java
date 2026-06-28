package com.threecolumnsstudio.simplegunpowder.forge;

import com.threecolumnsstudio.simplegunpowder.Platform;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(SimpleGunpowder.MOD_ID)
public class SimpleGunpowderForge {

    public SimpleGunpowderForge() {
        Platform.set(new ForgePlatform());
        SimpleGunpowder.init();
        MinecraftForge.EVENT_BUS.addListener(this::onVillagerTrades);
    }

    private void onVillagerTrades(VillagerTradesEvent event) {
        var type = event.getType();
        var key = BuiltInRegistries.VILLAGER_PROFESSION.getKey(type);
        if (!key.equals(new ResourceLocation("cleric")) &&
                !key.equals(new ResourceLocation("fletcher"))) return;
        event.getTrades().get(1).add((entity, random) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 2), new ItemStack(Items.GUNPOWDER, 10), 16, 2, 0.05F
        ));
    }
}

package com.threecolumnsstudio.simplegunpowder.forge;

import com.threecolumnsstudio.simplegunpowder.Platform;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(SimpleGunpowder.MOD_ID)
public class SimpleGunpowderForge {

    private static final ResourceLocation CLERIC = new ResourceLocation("cleric");
    private static final ResourceLocation FLETCHER = new ResourceLocation("fletcher");

    public SimpleGunpowderForge() {
        Platform.set(new ForgePlatform());
        SimpleGunpowder.init();
        MinecraftForge.EVENT_BUS.addListener(this::onVillagerTrades);
    }

    private void onVillagerTrades(VillagerTradesEvent event) {
        var type = event.getType();
        var key = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(type);
        if (!key.equals(CLERIC) &&
                !key.equals(FLETCHER)) return;
        event.getTrades().get(1).add((entity, random) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 2), new ItemStack(Items.GUNPOWDER, 10), 16, 2, 0.05F
        ));
    }
}

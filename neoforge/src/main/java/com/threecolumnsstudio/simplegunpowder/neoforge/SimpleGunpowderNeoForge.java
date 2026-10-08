package com.threecolumnsstudio.simplegunpowder.neoforge;

import com.threecolumnsstudio.simplegunpowder.Platform;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(SimpleGunpowder.MOD_ID)
public class SimpleGunpowderNeoForge {

    public SimpleGunpowderNeoForge(IEventBus modEventBus) {
        Platform.set(new NeoforgePlatform());
        SimpleGunpowder.init();

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            SimpleGunpowderNeoForgeClient.registerConfigScreen();
        }
    }
}

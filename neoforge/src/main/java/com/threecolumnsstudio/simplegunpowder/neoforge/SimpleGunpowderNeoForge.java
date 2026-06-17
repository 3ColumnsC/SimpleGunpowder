package com.threecolumnsstudio.simplegunpowder.neoforge;

import com.threecolumnsstudio.simplegunpowder.Platform;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(SimpleGunpowder.MOD_ID)
public class SimpleGunpowderNeoForge {

    public SimpleGunpowderNeoForge(IEventBus modEventBus) {
        Platform.set(new NeoforgePlatform());
        SimpleGunpowder.init();
    }
}

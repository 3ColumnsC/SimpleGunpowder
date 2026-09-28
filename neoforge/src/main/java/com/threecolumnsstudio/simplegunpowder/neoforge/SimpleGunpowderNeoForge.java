package com.threecolumnsstudio.simplegunpowder.neoforge;

import com.threecolumnsstudio.simplegunpowder.Platform;
import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import com.threecolumnsstudio.simplegunpowder.client.screen.SimpleGunpowderConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(SimpleGunpowder.MOD_ID)
public class SimpleGunpowderNeoForge {

    public SimpleGunpowderNeoForge(IEventBus modEventBus) {
        Platform.set(new NeoforgePlatform());
        SimpleGunpowder.init();

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            registerConfigScreen();
        }
    }

    private void registerConfigScreen() {
        ModList.get().getModContainerById(SimpleGunpowder.MOD_ID).ifPresent(container ->
            container.registerExtensionPoint(IConfigScreenFactory.class,
                (modContainer, parent) -> new SimpleGunpowderConfigScreen(parent)));
    }
}

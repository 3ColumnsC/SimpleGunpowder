package com.threecolumnsstudio.simplegunpowder.neoforge;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowder;
import com.threecolumnsstudio.simplegunpowder.neoforge.screen.SimpleGunpowderConfigScreen;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public final class SimpleGunpowderNeoForgeClient {

    private SimpleGunpowderNeoForgeClient() {}

    public static void registerConfigScreen() {
        ModList.get().getModContainerById(SimpleGunpowder.MOD_ID).ifPresent(container ->
            container.registerExtensionPoint(IConfigScreenFactory.class,
                (modContainer, parent) -> new SimpleGunpowderConfigScreen(parent)));
    }
}

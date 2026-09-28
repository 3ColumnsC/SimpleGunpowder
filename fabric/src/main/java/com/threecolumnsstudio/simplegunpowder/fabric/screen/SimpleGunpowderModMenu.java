package com.threecolumnsstudio.simplegunpowder.fabric.screen;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.threecolumnsstudio.simplegunpowder.client.screen.SimpleGunpowderConfigScreen;

public final class SimpleGunpowderModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SimpleGunpowderConfigScreen::new;
    }
}

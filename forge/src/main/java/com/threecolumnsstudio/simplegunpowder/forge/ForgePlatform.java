package com.threecolumnsstudio.simplegunpowder.forge;

import com.threecolumnsstudio.simplegunpowder.Platform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ForgePlatform implements Platform {

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}

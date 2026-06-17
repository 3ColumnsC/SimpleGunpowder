package com.threecolumnsstudio.simplegunpowder.fabric;

import com.threecolumnsstudio.simplegunpowder.datagen.SimpleGunpowderRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SimpleGunpowderFabricDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(SimpleGunpowderRecipeProvider::new);
    }
}

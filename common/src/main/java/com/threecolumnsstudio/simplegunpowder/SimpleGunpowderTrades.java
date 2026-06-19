package com.threecolumnsstudio.simplegunpowder;

public final class SimpleGunpowderTrades {

    private SimpleGunpowderTrades() {}

    public static void logConfiguration() {
        SimpleGunpowder.LOGGER.info(
                "Gunpowder trades are {}",
                SimpleGunpowderConfig.getInstance().enableTrades ? "enabled" : "disabled"
        );
    }
}
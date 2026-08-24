package com.threecolumnsstudio.simplegunpowder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimpleGunpowder {

    public static final String MOD_ID = "simplegunpowder";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private SimpleGunpowder() {}

    public static void init() {
        SimpleGunpowderConfig.load();
        LOGGER.info("{} initialized", MOD_ID);
    }
}

package com.threecolumnsstudio.simplegunpowder;

import java.nio.file.Path;

/**
 * Minimal platform abstraction — implemented separately in each loader module.
 * Add methods here only when behaviour truly differs between Fabric and NeoForge.
 */
public interface Platform {

    /** Returns the config directory for the current loader. */
    Path getConfigDir();

    // Retrieve the active instance (set at startup by each loader's entrypoint)
    static Platform get() {
        Platform platform = PlatformHolder.INSTANCE;
        if (platform == null) {
            throw new IllegalStateException(
                "Platform.get() called before Platform.set() — each loader entrypoint must call "
                + "Platform.set(...) before SimpleGunpowder.init()");
        }
        return platform;
    }

    static void set(Platform platform) {
        if (PlatformHolder.INSTANCE != null) {
            throw new IllegalStateException("Platform already set — Platform.set() must only be called once");
        }
        PlatformHolder.INSTANCE = platform;
    }
}

/** Package-private holder so Platform.set() stays in this package. */
class PlatformHolder {
    static volatile Platform INSTANCE;
}

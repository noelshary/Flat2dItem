package io.neoloni.flat2d;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Flat2DMod implements ClientModInitializer {
    public static final String MOD_ID = "flat2d";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        Flat2DConfig.load();
        LOGGER.info("2D Items geladen (aktiv: {})", Flat2DConfig.get().enabled);
    }
}

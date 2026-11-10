package io.flat2d;

import net.fabricmc.api.ClientModInitializer;

public class Flat2dClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Flat2dConfig.load();
    }
}

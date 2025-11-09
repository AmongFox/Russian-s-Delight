package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.registry.BlocksRegistry;
import net.fabricmc.api.ClientModInitializer;

public class RussiansDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlocksRegistry.registerRenderLayer();
    }
}

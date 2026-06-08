package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;

public class RussiansDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.registerRenderLayer();
    }
}

package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.registry.ModBlocks;
import com.amongfox.russiansdelight.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class RussiansDelightClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModBlocks.registerRenderLayer();
		registerColorProviders();
	}

	private void registerColorProviders() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			if (tintIndex == 0) {
				return 0xFFFFFFFF;
			}
			return 0xFFFFFFFF;
		}, ModItems.VODKA.get());
	}
}

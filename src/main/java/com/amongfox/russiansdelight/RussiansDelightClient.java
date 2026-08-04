package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.registry.ModBlocks;
import com.amongfox.russiansdelight.registry.ModItems;
import com.amongfox.russiansdelight.registry.ModMenus;
import com.amongfox.russiansdelight.screen.FermentingBarrelScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class RussiansDelightClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModBlocks.registerRenderLayer();
		registerColorProviders();
		registerScreens();
	}

	private void registerScreens() {
		ScreenRegistry.register(ModMenus.FERMENTING_BARREL.get(), FermentingBarrelScreen::new);
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

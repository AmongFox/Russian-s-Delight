package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.client.particle.FermentationBubbleParticle;
import com.amongfox.russiansdelight.registry.ModBlocks;
import com.amongfox.russiansdelight.registry.ModMenus;
import com.amongfox.russiansdelight.registry.ModParticles;
import com.amongfox.russiansdelight.screen.FermentingBarrelScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class RussiansDelightClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModBlocks.registerRenderLayer();
		registerParticles();
		registerScreens();
	}

	private void registerParticles() {
		ParticleFactoryRegistry.getInstance().register(ModParticles.FERMENTATION_BARREL_BUBBLE.get(), FermentationBubbleParticle.Provider::new);
	}

	private void registerScreens() {
		ScreenRegistry.register(ModMenus.FERMENTING_BARREL.get(), FermentingBarrelScreen::new);
	}
}

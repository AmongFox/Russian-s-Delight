package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.client.gui.SmallCookingPotScreen;
import com.amongfox.russiansdelight.registry.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class RussiansDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.SMALL_COOKING_POT, SmallCookingPotScreen::new);
    }
}

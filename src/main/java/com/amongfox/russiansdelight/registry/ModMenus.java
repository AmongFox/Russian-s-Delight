package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.screen.FermentingBarrelMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.flag.FeatureFlags;

import java.util.function.Supplier;

public enum ModMenus {
	FERMENTING_BARREL("fermenting_barrel", () -> new MenuType<>(FermentingBarrelMenu::new, FeatureFlags.VANILLA_SET));

	private final String pathName;
	private final Supplier<MenuType<?>> menuSupplier;
	private MenuType<?> menuType;
	private boolean registered = false;

	ModMenus(String pathName, Supplier<MenuType<?>> menuSupplier) {
		this.pathName = pathName;
		this.menuSupplier = menuSupplier;
	}

	public static void registerAll() {
		for (ModMenus value : values()) {
			value.register();
		}
	}

	private void register() {
		if (!registered) {
			this.menuType = Registry.register(
					BuiltInRegistries.MENU,
					ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, this.pathName),
					this.menuSupplier.get()
			);
			this.registered = true;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractContainerMenu> MenuType<T> get() {
		if (menuType == null) {
			throw new IllegalStateException("Menu " + this.name() + " not registered yet!");
		}
		return (MenuType<T>) menuType;
	}
}

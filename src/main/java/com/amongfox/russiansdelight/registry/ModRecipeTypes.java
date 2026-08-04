package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public enum ModRecipeTypes {
	FERMENTING("fermenting", () -> RecipeType.register(RussiansDelight.MOD_ID + ":fermenting"));

	private final String pathName;
	private final Supplier<RecipeType<?>> typeSupplier;
	private RecipeType<?> recipeType;
	private boolean registered = false;

	ModRecipeTypes(String pathName, Supplier<RecipeType<?>> typeSupplier) {
		this.pathName = pathName;
		this.typeSupplier = typeSupplier;
	}

	public static void registerAll() {
		for (ModRecipeTypes value : values()) {
			value.register();
		}
	}

	private void register() {
		if (!registered) {
			this.recipeType = this.typeSupplier.get();
			this.registered = true;
		}
	}

	public RecipeType<?> get() {
		if (recipeType == null) {
			throw new IllegalStateException("recipe type " + this.name() + " not registered yet!");
		}
		return recipeType;
	}
}

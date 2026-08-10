package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.recipe.FermentationRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

public enum ModRecipeSerializers {
	FERMENTING("fermenting", () -> new FermentationRecipeSerializer());

	private final String pathName;
	private final Supplier<RecipeSerializer<?>> serializerSupplier;
	private RecipeSerializer<?> serializer;
	private boolean registered = false;

	ModRecipeSerializers(String pathName, Supplier<RecipeSerializer<?>> serializerSupplier) {
		this.pathName = pathName;
		this.serializerSupplier = serializerSupplier;
	}

	public static void registerAll() {
		for (ModRecipeSerializers value : values()) {
			value.register();
		}
	}

	private void register() {
		if (!registered) {
			this.serializer = Registry.register(
					BuiltInRegistries.RECIPE_SERIALIZER,
					new ResourceLocation(RussiansDelight.MOD_ID, this.pathName),
					this.serializerSupplier.get()
			);
			this.registered = true;
		}
	}

	public RecipeSerializer<?> get() {
		if (serializer == null) {
			throw new IllegalStateException("recipe serializer " + this.name() + " not registered yet!");
		}
		return serializer;
	}
}

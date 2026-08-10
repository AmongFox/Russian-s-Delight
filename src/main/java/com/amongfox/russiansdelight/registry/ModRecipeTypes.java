package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.recipe.FermentationRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public enum ModRecipeTypes {
	FERMENTING("fermenting", () -> Registry.register(
			BuiltInRegistries.RECIPE_TYPE,
			ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, "fermenting"),
			new RecipeType<FermentationRecipe>() {
				@Override
				public String toString() {
					return RussiansDelight.MOD_ID + ":fermenting";
				}
			}
	));

    private final Supplier<RecipeType<?>> typeSupplier;
	private RecipeType<?> recipeType;
	private boolean registered = false;

	ModRecipeTypes(String pathName, Supplier<RecipeType<?>> typeSupplier) {
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

	@SuppressWarnings("unchecked")
	public <T extends Recipe<?>> RecipeType<T> get() {
		if (recipeType == null) {
			throw new IllegalStateException("recipe type " + this.name() + " not registered yet!");
		}
		return (RecipeType<T>) recipeType;
	}
}

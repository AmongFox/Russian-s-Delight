package com.amongfox.russiansdelight.recipe;

import com.amongfox.russiansdelight.registry.ModRecipeSerializers;
import com.amongfox.russiansdelight.registry.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class FermentationRecipe implements Recipe<Container> {
	private final ResourceLocation id;
	private final NonNullList<Ingredient> ingredients;
	private final ItemStack output;
	private final int duration;

	public FermentationRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack output, int duration) {
		this.id = id;
		this.ingredients = ingredients;
		this.output = output;
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	@Override
	public boolean matches(Container container, Level level) {
		for (Ingredient ingredient : ingredients) {
			int found = 0;
			for (int i = 0; i < 4; i++) {
				ItemStack slot = container.getItem(i);
				if (ingredient.test(slot)) {
					found += slot.getCount();
				}
			}
			if (found < 1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return output.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return output.copy();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.FERMENTING.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.FERMENTING.get();
	}
}

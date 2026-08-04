package com.amongfox.russiansdelight.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class FermentationRecipeSerializer implements RecipeSerializer<FermentationRecipe> {
	@Override
	public FermentationRecipe fromJson(ResourceLocation id, JsonObject json) {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
		for (JsonElement element : ingredientsJson) {
			ingredients.add(Ingredient.fromJson(element));
		}
		ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
		int duration = GsonHelper.getAsInt(json, "duration");
		return new FermentationRecipe(id, ingredients, output, duration);
	}

	@Override
	public FermentationRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
		int size = buffer.readVarInt();
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (int i = 0; i < size; i++) {
			ingredients.add(Ingredient.fromNetwork(buffer));
		}
		ItemStack output = buffer.readItem();
		int duration = buffer.readVarInt();
		return new FermentationRecipe(id, ingredients, output, duration);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, FermentationRecipe recipe) {
		buffer.writeVarInt(recipe.getIngredients().size());
		for (Ingredient ingredient : recipe.getIngredients()) {
			ingredient.toNetwork(buffer);
		}
		buffer.writeItem(recipe.getResultItem(RegistryAccess.EMPTY));
		buffer.writeVarInt(recipe.getDuration());
	}
}

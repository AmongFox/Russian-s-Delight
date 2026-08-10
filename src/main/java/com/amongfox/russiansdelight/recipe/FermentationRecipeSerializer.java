package com.amongfox.russiansdelight.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class FermentationRecipeSerializer implements RecipeSerializer<FermentationRecipe> {
	public static final MapCodec<FermentationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
					Codec.list(Ingredient.CODEC).fieldOf("ingredients").forGetter(FermentationRecipe::getIngredients),
					ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.getResultItem(RegistryAccess.EMPTY)),
					Codec.INT.fieldOf("duration").forGetter(FermentationRecipe::getDuration),
					Ingredient.CODEC.fieldOf("container").forGetter(FermentationRecipe::getContainer)
			).apply(instance, (ingredients, result, duration, container) ->
					new FermentationRecipe(toIngredients(ingredients), result, duration, container))
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, FermentationRecipe> STREAM_CODEC = StreamCodec.composite(
			Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
			FermentationRecipe::getIngredients,
			ItemStack.OPTIONAL_STREAM_CODEC,
			recipe -> recipe.getResultItem(RegistryAccess.EMPTY),
			ByteBufCodecs.VAR_INT,
			FermentationRecipe::getDuration,
			Ingredient.CONTENTS_STREAM_CODEC,
			FermentationRecipe::getContainer,
			FermentationRecipeSerializer::create
	);

	private static NonNullList<Ingredient> toIngredients(java.util.List<Ingredient> ingredients) {
		return NonNullList.of(Ingredient.EMPTY, ingredients.toArray(Ingredient[]::new));
	}

	private static FermentationRecipe create(java.util.List<Ingredient> ingredients, ItemStack result, int duration, Ingredient container) {
		return new FermentationRecipe(toIngredients(ingredients), result, duration, container);
	}

	@Override
	public @NotNull MapCodec<FermentationRecipe> codec() {
		return CODEC;
	}

	@Override
	public @NotNull StreamCodec<RegistryFriendlyByteBuf, FermentationRecipe> streamCodec() {
		return STREAM_CODEC;
	}
}

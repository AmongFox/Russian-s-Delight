package com.amongfox.russiansdelight.integration.jei;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.recipe.FermentationRecipe;
import com.amongfox.russiansdelight.registry.ModItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FermentationCategory implements IRecipeCategory<FermentationRecipe> {
	private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, "textures/gui/fermentation_barrel_gui.png");
	private static final int DEFAULT_DURATION = 240 * 20;

	private final IDrawable background;
	private final IDrawable icon;
    private final IDrawableAnimated animatedScale;

	public FermentationCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(GUI_TEXTURE, 4, 21, 156, 54);
		this.icon = helper.createDrawableItemStack(new ItemStack(ModItems.FERMENTATION_BARREL.get()));
        IDrawableStatic filledScale = helper.createDrawable(GUI_TEXTURE, 176, 16, 8, 36);
		this.animatedScale = helper.createAnimatedDrawable(
                filledScale, DEFAULT_DURATION, IDrawableAnimated.StartDirection.BOTTOM, false);
	}

	@Override
	public @NotNull RecipeType<FermentationRecipe> getRecipeType() {
		return JEIPlugin.FERMENTING_TYPE;
	}

	@Override
	public @NotNull Component getTitle() {
		return Component.translatable("russiansdelight.jei.category.fermenting");
	}

	@Override
	public int getWidth() {
		return background.getWidth();
	}

	@Override
	public int getHeight() {
		return background.getHeight();
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(@NotNull IRecipeLayoutBuilder builder, FermentationRecipe recipe, @NotNull IFocusGroup focuses) {
		int[][] inputPositions = {{22, 4}, {40, 4}, {22, 22}, {40, 22}};
		List<Ingredient> ingredients = recipe.getIngredients();
		for (int i = 0; i < inputPositions.length; i++) {
			if (i < ingredients.size()) {
				builder.addSlot(RecipeIngredientRole.INPUT, inputPositions[i][0], inputPositions[i][1])
						.addIngredients(ingredients.get(i));
			}
		}
		builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 13)
				.addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
		builder.addSlot(RecipeIngredientRole.INPUT, 103, 34)
				.addIngredients(recipe.getContainer());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 34)
				.addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
	}

	@Override
	public void draw(@NotNull FermentationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 0);
		animatedScale.draw(guiGraphics, 60, 3);
	}
}

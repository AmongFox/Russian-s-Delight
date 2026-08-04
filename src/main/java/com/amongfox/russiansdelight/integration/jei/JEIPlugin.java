package com.amongfox.russiansdelight.integration.jei;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.recipe.FermentationRecipe;
import com.amongfox.russiansdelight.registry.ModItems;
import com.amongfox.russiansdelight.registry.ModRecipeTypes;
import com.amongfox.russiansdelight.screen.FermentingBarrelScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
	private static final ResourceLocation ID = new ResourceLocation(RussiansDelight.MOD_ID, "jei_plugin");
	public static final RecipeType<FermentationRecipe> FERMENTING_TYPE =
			RecipeType.create(RussiansDelight.MOD_ID, "fermenting", FermentationRecipe.class);

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new FermentationCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addIngredientInfo(
			List.of(new ItemStack(ModItems.WILD_CUCUMBER.get()), new ItemStack(ModItems.CUCUMBER.get())),
			VanillaTypes.ITEM_STACK,
			Component.translatable("russiansdelight.jei.info.wild_cucumber")
		);

		Level level = Minecraft.getInstance().level;
		if (level != null) {
			List<FermentationRecipe> fermentingRecipes = level.getRecipeManager().getAllRecipesFor(
					(net.minecraft.world.item.crafting.RecipeType<FermentationRecipe>) ModRecipeTypes.FERMENTING.get());
			registration.addRecipes(FERMENTING_TYPE, fermentingRecipes);
		}
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ModItems.FERMENTATION_BARREL.get()), FERMENTING_TYPE);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(FermentingBarrelScreen.class, 64, 24, 8, 36, FERMENTING_TYPE);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}
}

package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.item.FoodItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SuspiciousStewItem;

import java.util.Arrays;
import java.util.function.Supplier;

public enum ModItems {
	// Other
	CUCUMBER("cucumber", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.CUCUMBER))),

	// Bakery
	PANCAKES("pancakes", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.PANCAKES).stacksTo(0)), false),
	PIECE_FISH_PIE("piece_fish_pie", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.PIECE_FISH_PIE).stacksTo(0)), false),
	CABBAGE_PIE("cabbage_pie", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.CABBAGE_PIE).stacksTo(16))),
	BERRY_PIE("berry_pie", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.BERRY_PIE).stacksTo(16))),

	// Супы
	BORSCHT("bowl_of_borscht", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.BORSCHT).stacksTo(1))),
	SHCHI("bowl_of_shchi", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.SHCHI).stacksTo(1))),
	SOLYANKA("bowl_of_solyanka", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.SOLYANKA).stacksTo(1))),
	RASSOLNIK("bowl_of_rassolnik", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.RASSOLNIK).stacksTo(1))),

	// Main courses
	ROAST("bowl_of_roast", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.ROAST).stacksTo(1))),
	PELMENI("bowl_of_pelmeni", () -> new SuspiciousStewItem(createFoodSettings(FoodItem.PELMENI).stacksTo(1))),

	// Blocks
	SMALL_POT("small_pot", () -> new BlockItem(ModBlocks.SMALL_POT.get(), new Item.Properties())),
	BORSCHT_POT("borscht_pot", () -> new BlockItem(ModBlocks.BORSCHT_POT.get(), new Item.Properties())),
	SHCHI_POT("shchi_pot", () -> new BlockItem(ModBlocks.SHCHI_POT.get(), new Item.Properties())),
	SOLYANKA_POT("solyanka_pot", () -> new BlockItem(ModBlocks.SOLYANKA_POT.get(), new Item.Properties())),
	RASSOLNIK_POT("rassolnik_pot", () -> new BlockItem(ModBlocks.RASSOLNIK_POT.get(), new Item.Properties())),
	PANCAKES_TRAY("pancakes_tray", () -> new BlockItem(ModBlocks.PANCAKES_TRAY.get(), new Item.Properties())),
	FISH_PIE("fish_pie", () -> new BlockItem(ModBlocks.FISH_PIE.get(), new Item.Properties())),
	CABBAGE_PIES_TRAY("cabbage_pies_tray", () -> new BlockItem(ModBlocks.CABBAGE_PIES_TRAY.get(), new Item.Properties())),
	BERRIES_PIES_TRAY("berries_pies_tray", () -> new BlockItem(ModBlocks.BERRIES_PIES_TRAY.get(), new Item.Properties())),

	// Crops
	WILD_CUCUMBER("wild_cucumber", () -> new BlockItem(ModBlocks.WILD_CUCUMBER.get(), new Item.Properties()));

	private final String pathName;
	private final Supplier<Item> itemSupplier;
	private final boolean addToCreativeTab;
	private Item item;
	private boolean registered = false;

	private static Item.Properties createFoodSettings(FoodItem foodItem) {
		return new Item.Properties().food(foodItem.getFoodComponent());
	}

	ModItems(String pathName, Supplier<Item> itemSupplier) {
		this.pathName = pathName;
		this.itemSupplier = itemSupplier;
		this.addToCreativeTab = true;
	}

	ModItems(String pathName, Supplier<Item> itemSupplier, boolean addToCreativeTab) {
		this.pathName = pathName;
		this.itemSupplier = itemSupplier;
		this.addToCreativeTab = addToCreativeTab;
	}

	public static void registerAll() {
		for (ModItems value : values()) {
			value.register();
		}
	}

	private void register() {
		if (!registered) {
			this.item = Registry.register(
					BuiltInRegistries.ITEM,
					new ResourceLocation(RussiansDelight.MOD_ID, this.pathName),
					this.itemSupplier.get()
			);
			this.registered = true;
		}
	}

	public Item get() {
		if (item == null) {
			throw new IllegalStateException("item " + this.name() + " not registered yet!");
		}
		return item;
	}

	public static ModItems[] getItemsRegistryForCreativeTab() {
		return Arrays.stream(values())
				.filter(ModItems::shouldAddToCreativeTab)
				.toArray(ModItems[]::new);
	}

	private boolean shouldAddToCreativeTab() {
		return addToCreativeTab;
	}
}

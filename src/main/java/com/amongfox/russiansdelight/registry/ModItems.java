package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.item.FoodItem;
import com.amongfox.russiansdelight.item.KvassItem;
import com.amongfox.russiansdelight.item.PotBlockItem;
import com.amongfox.russiansdelight.item.VodkaItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public enum ModItems {
	// Other
	CUCUMBER("cucumber", () -> new Item(createFoodSettings(FoodItem.CUCUMBER))),
	BUTTER("butter", () -> new Item(createFoodSettings(FoodItem.BUTTER))),
	WOODEN_MUG("wooden_mug", () -> new Item(new Item.Properties().stacksTo(16))),

	// Drinks
	KVASS("kvass", () -> new KvassItem(createFoodSettings(FoodItem.KVASS).stacksTo(16))),
	VODKA("vodka", () -> new VodkaItem(createFoodSettings(FoodItem.VODKA).stacksTo(1))),
	LARGE_GLASS_BOTTLE("large_glass_bottle", () -> new Item(new Item.Properties())),

	// Bakery
	PANCAKES("pancakes", () -> new Item(createFoodSettings(FoodItem.PANCAKES)), false),
	PIECE_FISH_PIE("piece_fish_pie", () -> new Item(createFoodSettings(FoodItem.PIECE_FISH_PIE)), false),
	CABBAGE_PIE("cabbage_pie", () -> new Item(createFoodSettings(FoodItem.CABBAGE_PIE).stacksTo(16))),
	BERRY_PIE("berry_pie", () -> new Item(createFoodSettings(FoodItem.BERRY_PIE).stacksTo(16))),

	// Супы
	BORSCHT("bowl_of_borscht", () -> new Item(createFoodSettings(FoodItem.BORSCHT).stacksTo(1))),
	SHCHI("bowl_of_shchi", () -> new Item(createFoodSettings(FoodItem.SHCHI).stacksTo(1))),
	SOLYANKA("bowl_of_solyanka", () -> new Item(createFoodSettings(FoodItem.SOLYANKA).stacksTo(1))),
	RASSOLNIK("bowl_of_rassolnik", () -> new Item(createFoodSettings(FoodItem.RASSOLNIK).stacksTo(1))),
	OKROSHKA("bowl_of_okroshka", () -> new Item(createFoodSettings(FoodItem.OKROSHKA).stacksTo(1))),

	// Main courses
	ROAST("bowl_of_roast", () -> new Item(createFoodSettings(FoodItem.ROAST).stacksTo(1))),
	PELMENI("bowl_of_pelmeni", () -> new Item(createFoodSettings(FoodItem.PELMENI).stacksTo(1))),
	DUMPLING("dumpling", () -> new Item(createFoodSettings(FoodItem.DUMPLING))),
	BREAD_AND_BUTTER("bread_and_butter", () -> new Item(createFoodSettings(FoodItem.BREAD_AND_BUTTER))),
	SEMOLINA("semolina", () -> new Item(new Item.Properties())),
	SEMOLINA_PORRIDGE("bowl_of_semolina_porridge", () -> new Item(createFoodSettings(FoodItem.SEMOLINA_PORRIDGE).stacksTo(1))),

	// Blocks
	SMALL_POT("small_pot", () -> new BlockItem(ModBlocks.SMALL_POT.get(), new Item.Properties())),
	BORSCHT_POT("borscht_pot", () -> new PotBlockItem(ModBlocks.BORSCHT_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties())),
	SHCHI_POT("shchi_pot", () -> new PotBlockItem(ModBlocks.SHCHI_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties())),
	SOLYANKA_POT("solyanka_pot", () -> new PotBlockItem(ModBlocks.SOLYANKA_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties())),
	RASSOLNIK_POT("rassolnik_pot", () -> new PotBlockItem(ModBlocks.RASSOLNIK_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties())),
	OKROSHKA_POT("okroshka_pot", () -> new PotBlockItem(ModBlocks.OKROSHKA_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties())),
	PANCAKES_TRAY("pancakes_tray", () -> new BlockItem(ModBlocks.PANCAKES_TRAY.get(), new Item.Properties())),
	FISH_PIE("fish_pie", () -> new BlockItem(ModBlocks.FISH_PIE.get(), new Item.Properties())),
	CABBAGE_PIES_TRAY("cabbage_pies_tray", () -> new BlockItem(ModBlocks.CABBAGE_PIES_TRAY.get(), new Item.Properties())),
	BERRIES_PIES_TRAY("berries_pies_tray", () -> new BlockItem(ModBlocks.BERRIES_PIES_TRAY.get(), new Item.Properties())),

	// Crops
	WILD_CUCUMBER("wild_cucumber", () -> new BlockItem(ModBlocks.WILD_CUCUMBER.get(), new Item.Properties())),
	CUCUMBER_SEEDS("cucumber_seeds", () -> new ItemNameBlockItem(ModBlocks.BUDDING_CUCUMBER_CROP.get(), new Item.Properties())
	{
		@Override
		public void registerBlocks(@NotNull Map<Block, Item> blockToItemMap, @NotNull Item item) {
			super.registerBlocks(blockToItemMap, item);
			blockToItemMap.put(ModBlocks.CUCUMBER_CROP.get(), item);
		}

		@Override
		public void removeFromBlockToItemMap(@NotNull Map<Block, Item> blockToItemMap, @NotNull Item itemIn) {
			super.removeFromBlockToItemMap(blockToItemMap, itemIn);
			blockToItemMap.remove(ModBlocks.CUCUMBER_CROP.get());
		}
	}),

	// Machines
	FERMENTATION_BARREL("fermentation_barrel", () -> new BlockItem(ModBlocks.FERMENTATION_BARREL.get(), new Item.Properties()));

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

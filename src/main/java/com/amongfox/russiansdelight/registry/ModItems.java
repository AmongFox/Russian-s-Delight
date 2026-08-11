package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.item.FoodItem;
import com.amongfox.russiansdelight.item.KvassItem;
import com.amongfox.russiansdelight.item.PotBlockItem;
import com.amongfox.russiansdelight.item.VodkaItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public enum ModItems {
	// Other
    CUCUMBER("cucumber", () -> new Item(createFoodSettings(FoodItem.CUCUMBER).setId(key("cucumber")))),
    BUTTER("butter", () -> new Item(createFoodSettings(FoodItem.BUTTER).setId(key("butter")))),
	WOODEN_MUG("wooden_mug", () -> new Item(new Item.Properties().stacksTo(16).setId(key("wooden_mug")))),

	// Drinks
	KVASS("kvass", () -> new KvassItem(createFoodSettings(FoodItem.KVASS).stacksTo(16).setId(key("kvass")))),
	VODKA("vodka", () -> new VodkaItem(createFoodSettings(FoodItem.VODKA).stacksTo(1).setId(key("vodka")))),
	LARGE_GLASS_BOTTLE("large_glass_bottle", () -> new Item(new Item.Properties().setId(key("large_glass_bottle")))),

	// Bakery
	PANCAKES("pancakes", () -> new Item(createFoodSettings(FoodItem.PANCAKES).setId(key("pancakes"))), false),
	PIECE_FISH_PIE("piece_fish_pie", () -> new Item(createFoodSettings(FoodItem.PIECE_FISH_PIE).setId(key("piece_fish_pie"))), false),
	CABBAGE_PIE("cabbage_pie", () -> new Item(createFoodSettings(FoodItem.CABBAGE_PIE).stacksTo(16).setId(key("cabbage_pie")))),
	BERRY_PIE("berry_pie", () -> new Item(createFoodSettings(FoodItem.BERRY_PIE).stacksTo(16).setId(key("berry_pie")))),

	// Супы
	BORSCHT("bowl_of_borscht", () -> new Item(createFoodSettings(FoodItem.BORSCHT).stacksTo(1).setId(key("bowl_of_borscht")))),
	SHCHI("bowl_of_shchi", () -> new Item(createFoodSettings(FoodItem.SHCHI).stacksTo(1).setId(key("bowl_of_shchi")))),
	SOLYANKA("bowl_of_solyanka", () -> new Item(createFoodSettings(FoodItem.SOLYANKA).stacksTo(1).setId(key("bowl_of_solyanka")))),
	RASSOLNIK("bowl_of_rassolnik", () -> new Item(createFoodSettings(FoodItem.RASSOLNIK).stacksTo(1).setId(key("bowl_of_rassolnik")))),
    OKROSHKA("bowl_of_okroshka", () -> new Item(createFoodSettings(FoodItem.OKROSHKA).stacksTo(1).setId(key("bowl_of_okroshka")))),

	// Main courses
	ROAST("bowl_of_roast", () -> new Item(createFoodSettings(FoodItem.ROAST).stacksTo(1).setId(key("bowl_of_roast")))),
	PELMENI("bowl_of_pelmeni", () -> new Item(createFoodSettings(FoodItem.PELMENI).stacksTo(1).setId(key("bowl_of_pelmeni")))),
	DUMPLING("dumpling", () -> new Item(createFoodSettings(FoodItem.DUMPLING).setId(key("dumpling")))),
	BREAD_AND_BUTTER("bread_and_butter", () -> new Item(createFoodSettings(FoodItem.BREAD_AND_BUTTER).setId(key("bread_and_butter")))),
	SEMOLINA("semolina", () -> new Item(new Item.Properties().setId(key("semolina")))),
	SEMOLINA_PORRIDGE("bowl_of_semolina_porridge", () -> new Item(createFoodSettings(FoodItem.SEMOLINA_PORRIDGE).stacksTo(1).setId(key("bowl_of_semolina_porridge")))),
    BUCKWHEAT("buckwheat", () -> new Item(new Item.Properties().setId(key("buckwheat")))),
    BUCKWHEAT_PORRIDGE("bowl_of_buckwheat_porridge", () -> new Item(createFoodSettings(FoodItem.BUCKWHEAT_PORRIDGE).stacksTo(1).setId(key("bowl_of_buckwheat_porridge")))),

	// Blocks
	SMALL_POT("small_pot", () -> new BlockItem(ModBlocks.SMALL_POT.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("small_pot")))),
	BORSCHT_POT("borscht_pot", () -> new PotBlockItem(ModBlocks.BORSCHT_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("borscht_pot")))),
	SHCHI_POT("shchi_pot", () -> new PotBlockItem(ModBlocks.SHCHI_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("shchi_pot")))),
	SOLYANKA_POT("solyanka_pot", () -> new PotBlockItem(ModBlocks.SOLYANKA_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("solyanka_pot")))),
	RASSOLNIK_POT("rassolnik_pot", () -> new PotBlockItem(ModBlocks.RASSOLNIK_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("rassolnik_pot")))),
    OKROSHKA_POT("okroshka_pot", () -> new PotBlockItem(ModBlocks.OKROSHKA_POT.get(), ModItems.SMALL_POT.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("okroshka_pot")))),
    PANCAKES_TRAY("pancakes_tray", () -> new BlockItem(ModBlocks.PANCAKES_TRAY.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("pancakes_tray")))),
	FISH_PIE("fish_pie", () -> new BlockItem(ModBlocks.FISH_PIE.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("fish_pie")))),
	CABBAGE_PIES_TRAY("cabbage_pies_tray", () -> new BlockItem(ModBlocks.CABBAGE_PIES_TRAY.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("cabbage_pies_tray")))),
	BERRIES_PIES_TRAY("berries_pies_tray", () -> new BlockItem(ModBlocks.BERRIES_PIES_TRAY.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("berries_pies_tray")))),

	// Crops
	WILD_CUCUMBER("wild_cucumber", () -> new BlockItem(ModBlocks.WILD_CUCUMBER.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("wild_cucumber")))),
	WILD_BUCKWHEAT("wild_buckwheat", () -> new BlockItem(ModBlocks.WILD_BUCKWHEAT.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("wild_buckwheat")))),
	BUCKWHEAT_SEEDS("buckwheat_seeds", () -> new BlockItem(ModBlocks.BUDDING_BUCKWHEAT_CROP.get(), new Item.Properties().setId(key("buckwheat_seeds")))),
	CUCUMBER_SEEDS("cucumber_seeds", () -> new BlockItem(ModBlocks.BUDDING_CUCUMBER_CROP.get(), new Item.Properties().setId(key("cucumber_seeds")))
	{
		@Override
		public void registerBlocks(@NotNull Map<Block, Item> blockToItemMap, @NotNull Item item) {
			super.registerBlocks(blockToItemMap, item);
			blockToItemMap.put(ModBlocks.CUCUMBER_CROP.get(), item);
		}
	}),

	// Machines
	FERMENTATION_BARREL("fermentation_barrel", () -> new BlockItem(ModBlocks.FERMENTATION_BARREL.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key("fermentation_barrel"))));

	private final String pathName;
	private final Supplier<Item> itemSupplier;
	private final boolean addToCreativeTab;
	private Item item;
	private boolean registered = false;

	private static Item.Properties createFoodSettings(FoodItem foodItem) {
		if (foodItem.getConsumable() != null) {
			return new Item.Properties().food(foodItem.getFoodComponent(), foodItem.getConsumable());
		}
		return new Item.Properties().food(foodItem.getFoodComponent());
	}

	private static ResourceKey<Item> key(String name) {
		return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, name));
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
					ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, this.pathName),
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

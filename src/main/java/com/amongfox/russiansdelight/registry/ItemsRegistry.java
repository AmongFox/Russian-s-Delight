package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.item.FoodItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.StewItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.function.Supplier;

public enum ItemsRegistry {
    // No registration for the group
    PANCAKES("pancakes", () -> new StewItem(createFoodSettings(FoodItem.PANCAKES).maxCount(0)), false),
    PIECE_FISH_PIE("piece_fish_pie", () -> new StewItem(createFoodSettings(FoodItem.PIECE_FISH_PIE).maxCount(0)), false),
    PIECE_CABBAGE_PIE("piece_cabbage_pie", () -> new StewItem(createFoodSettings(FoodItem.PIECE_CABBAGE_PIE).maxCount(0)), false),

    // Other
    CUCUMBER("cucumber", () -> new StewItem(createFoodSettings(FoodItem.CUCUMBER))),

    // Bakery
    CABBAGE_PIE("cabbage_pie", () -> new StewItem(createFoodSettings(FoodItem.CABBAGE_PIE))), 

    // Food items
    BORSCHT("bowl_of_borscht", () -> new StewItem(createFoodSettings(FoodItem.BORSCHT).maxCount(1))),
    SHCHI("bowl_of_shchi", () -> new StewItem(createFoodSettings(FoodItem.SHCHI).maxCount(1))),
    SOLYANKA("bowl_of_solyanka", () -> new StewItem(createFoodSettings(FoodItem.SOLYANKA).maxCount(1))),
    RASSOLNIK("bowl_of_rassolnik", () -> new StewItem(createFoodSettings(FoodItem.RASSOLNIK).maxCount(1))),

    // Food blocks
    SMALL_POT("small_pot", () -> new BlockItem(BlocksRegistry.SMALL_POT.get(), new Item.Settings())),
    BORSCHT_POT("borscht_pot", () -> new BlockItem(BlocksRegistry.BORSCHT_POT.get(), new Item.Settings())),
    SHCHI_POT("shchi_pot", () -> new BlockItem(BlocksRegistry.SHCHI_POT.get(), new Item.Settings())),
    SOLYANKA_POT("solyanka_pot", () -> new BlockItem(BlocksRegistry.SOLYANKA_POT.get(), new Item.Settings())),
    RASSOLNIK_POT("rassolnik_pot", () -> new BlockItem(BlocksRegistry.RASSOLNIK_POT.get(), new Item.Settings())),
    PANCAKES_TRAY("pancakes_tray", () -> new BlockItem(BlocksRegistry.PANCAKES_TRAY.get(), new Item.Settings())),
    FISH_PIE("fish_pie", () -> new BlockItem(BlocksRegistry.FISH_PIE.get(), new Item.Settings())),
    CABBAGE_PIES_TRAY("cabbage_pies_tray", () -> new BlockItem(BlocksRegistry.CABBAGE_PIES_TRAY.get(), new Item.Settings()));

    private final String pathName;
    private final Supplier<Item> itemSupplier;
    private final boolean addToCreativeTab;
    private Item item;
    private boolean registered = false;

    private static Item.Settings createFoodSettings(FoodItem foodItem) {
        return new Item.Settings().food(foodItem.getFoodComponent());
    }

    ItemsRegistry(String pathName, Supplier<Item> itemSupplier) {
        this.pathName = pathName;
        this.itemSupplier = itemSupplier;
        this.addToCreativeTab = true;
    }

    ItemsRegistry(String pathName, Supplier<Item> itemSupplier, boolean addToCreativeTab) {
        this.pathName = pathName;
        this.itemSupplier = itemSupplier;
        this.addToCreativeTab = addToCreativeTab;
    }

    public static void registerAll() {
        for (ItemsRegistry value : values()) {
            value.register();
        }
    }

    private void register() {
        if (!registered) {
            this.item = Registry.register(
                    Registries.ITEM,
                    new Identifier(RussiansDelight.MOD_ID, this.pathName),
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

    public static ItemsRegistry[] getItemsRegistryForCreativeTab() {
        return Arrays.stream(values())
                .filter(ItemsRegistry::shouldAddToCreativeTab)
                .toArray(ItemsRegistry[]::new);
    }

    private boolean shouldAddToCreativeTab() {
        return addToCreativeTab;
    }
}

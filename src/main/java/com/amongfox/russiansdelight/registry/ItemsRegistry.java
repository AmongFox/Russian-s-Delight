package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.StewItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public enum ItemsRegistry {
    BORSCHT("bowl_of_borscht", () -> new StewItem(createFoodSettings(FoodItem.BORSCHT).maxCount(1)));

    private final String pathName;
    private final Supplier<Item> itemSupplier;
    private Item item;
    private boolean registered = false;

    private static Item.Settings createFoodSettings(FoodItem foodItem) {
        return new Item.Settings().food(foodItem.getFoodComponent());
    }

    ItemsRegistry(String pathName, Supplier<Item> itemSupplier) {
        this.pathName = pathName;
        this.itemSupplier = itemSupplier;
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
            throw new IllegalStateException("item" + this.name() + " not registered yet!");
        }
        return item;
    }
}

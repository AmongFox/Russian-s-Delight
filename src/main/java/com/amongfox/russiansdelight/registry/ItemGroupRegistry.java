package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class ItemGroupRegistry {
    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(RussiansDelight.MOD_ITEM_GROUP).register(entries -> {
            for (ItemsRegistry item : ItemsRegistry.values()) {
                entries.add(item.get());
            }
        });
    }
}

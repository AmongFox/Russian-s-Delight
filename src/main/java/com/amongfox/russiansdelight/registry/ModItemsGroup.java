package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.ItemStack;

public class ModItemsGroup {
	public static void registerItemGroups() {
		ItemGroupEvents.modifyEntriesEvent(RussiansDelight.MOD_ITEM_GROUP).register(entries -> {
			for (ModItems item : ModItems.getItemsRegistryForCreativeTab()) {
				entries.accept(new ItemStack(item.get()));
			}
		});
	}
}

package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.registry.ModBlocks;
import com.amongfox.russiansdelight.registry.ModItemsGroup;
import com.amongfox.russiansdelight.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RussiansDelight implements ModInitializer {
	public static final String MOD_ID = "russiansdelight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ResourceKey<CreativeModeTab> MOD_ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(MOD_ID));

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		Component groupName = Component.translatable("itemgroup.russiansdelight.group");
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ITEM_GROUP, FabricItemGroup.builder()
				.title(groupName)
				.icon(() -> new ItemStack(ModItems.BORSCHT.get()))
				.build());

		ModBlocks.registerAll();
		ModItems.registerAll();
		ModItemsGroup.registerItemGroups();

		LOGGER.info(MOD_ID + " successfully initialized");
	}
}

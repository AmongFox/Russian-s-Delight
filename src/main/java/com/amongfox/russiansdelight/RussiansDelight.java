package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.registry.BlocksRegistry;
import com.amongfox.russiansdelight.registry.ItemGroupRegistry;
import com.amongfox.russiansdelight.registry.ItemsRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RussiansDelight implements ModInitializer {
	public static final String MOD_ID = "russiansdelight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final RegistryKey<ItemGroup> MOD_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID));

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		Registry.register(Registries.ITEM_GROUP, MOD_ITEM_GROUP, FabricItemGroup.builder()
				.displayName(Text.translatable("itemgroup.russiansdelight.group"))
				.icon(() -> new ItemStack(ItemsRegistry.BORSCHT.get()))
				.build());

		BlocksRegistry.registerAll();
		ItemsRegistry.registerAll();
		ItemGroupRegistry.registerItemGroups();

		LOGGER.info(MOD_ID + " successfully initialized");
	}
}
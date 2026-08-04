package com.amongfox.russiansdelight;

import com.amongfox.russiansdelight.registry.ModBlockEntities;
import com.amongfox.russiansdelight.registry.ModBlocks;
import com.amongfox.russiansdelight.registry.ModItemsGroup;
import com.amongfox.russiansdelight.registry.ModItems;
import com.amongfox.russiansdelight.registry.ModMenus;
import com.amongfox.russiansdelight.registry.ModRecipeSerializers;
import com.amongfox.russiansdelight.registry.ModRecipeTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RussiansDelight implements ModInitializer {
	public static final String MOD_ID = "russiansdelight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ResourceKey<CreativeModeTab> MOD_ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(MOD_ID));
	private static final ResourceKey<PlacedFeature> PATCH_WILD_CUCUMBER = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MOD_ID, "patch_wild_cucumber"));
	private static final TagKey<Biome> WILD_CUCUMBER_WHITELIST = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "wild_cucumber_whitelist"));
	private static final TagKey<Biome> WILD_CUCUMBER_BLACKLIST = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "wild_cucumber_blacklist"));

	@Override
	public void onInitialize() {
		LOGGER.info("Load RussiansDelight");
		Component groupName = Component.translatable("itemgroup.russiansdelight.group");
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ITEM_GROUP, FabricItemGroup.builder()
				.title(groupName)
				.icon(() -> new ItemStack(ModItems.BORSCHT.get()))
				.build());

		ModBlocks.registerAll();
		ModBlockEntities.registerAll();
		ModItems.registerAll();
		ModMenus.registerAll();
		ModRecipeTypes.registerAll();
		ModRecipeSerializers.registerAll();
		ModItemsGroup.registerItemGroups();
		registerCompostables();
		registerWorldgen();

		LOGGER.info(MOD_ID + " successfully initialized");
	}

	private static void registerWorldgen() {
		BiomeModifications.addFeature(
			context -> {
				Holder<Biome> biome = context.getBiomeRegistryEntry();
				return biome.is(WILD_CUCUMBER_WHITELIST) && !biome.is(WILD_CUCUMBER_BLACKLIST);
			},
			GenerationStep.Decoration.VEGETAL_DECORATION,
			PATCH_WILD_CUCUMBER
		);
		LOGGER.info("Worldgen registered for wild cucumber");
	}

	private static void registerCompostables() {
		CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;
		registry.add(ModItems.CUCUMBER_SEEDS.get(), 0.3f);
		registry.add(ModItems.CUCUMBER.get(), 0.65f);
	}
}

package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.block.*;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.amongfox.russiansdelight.RussiansDelight.LOGGER;

public enum ModBlocks {
	// Blocks
	SMALL_POT("small_pot", SmallPotBlock::new, potProps("small_pot"), true),

	// Soups
	BORSCHT_POT("borscht_pot", BorschtPotBlock::new, potProps("borscht_pot"), true),
	SHCHI_POT("shchi_pot", ShchiPotBlock::new, potProps("shchi_pot"), true),
	SOLYANKA_POT("solyanka_pot",SolyankaPotBlock::new, potProps("solyanka_pot"), true),
	RASSOLNIK_POT("rassolnik_pot", RassolnikPotBlock::new, potProps("rassolnik_pot"), true),
	OKROSHKA_POT("okroshka_pot", OkroshkaPotBlock::new, potProps("okroshka_pot"), true),

	// Bakery
	PANCAKES_TRAY("pancakes_tray", PancakesTrayBlock::new, trayProps("pancakes_tray"), true),
	FISH_PIE("fish_pie", FishPieBlock::new, trayProps("fish_pie"), true),
	CABBAGE_PIES_TRAY("cabbage_pies_tray", CabbagePiesTrayBlock::new, trayProps("cabbage_pies_tray"), true),
	BERRIES_PIES_TRAY("berries_pies_tray", BerriesPiesTrayBlock::new, trayProps("berries_pies_tray"), true),

	// Crops
    WILD_CUCUMBER("wild_cucumber", props -> new WildCucumberBlock(
            MobEffects.HUNGER, 6, props
    ), Block.Properties.ofFullCopy(Blocks.TALL_GRASS).noCollission().instabreak().sound(SoundType.GRASS).setId(key("wild_cucumber")), true),
    BUDDING_CUCUMBER_CROP("budding_cucumber_crop", BuddingCucumberBlock::new, Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).setId(key("budding_cucumber_crop")), true),
    CUCUMBER_CROP("cucumbers", CucumberVineBlock::new, Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).setId(key("cucumbers")), true),
	WILD_BUCKWHEAT("wild_buckwheat", props -> new WildBuckwheatBlock(
			MobEffects.HUNGER, 6, props
	), Block.Properties.ofFullCopy(Blocks.TALL_GRASS).noCollission().instabreak().sound(SoundType.GRASS).setId(key("wild_buckwheat")), true),
	BUDDING_BUCKWHEAT_CROP("budding_buckwheat_crop", BuddingBuckwheatBlock::new, Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).setId(key("budding_buckwheat_crop")), true),
	BUCKWHEAT_CROP("buckwheat_crop", BuckwheatCropBlock::new, Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).setId(key("buckwheat_crop")), true),

	// Machines
	FERMENTATION_BARREL("fermentation_barrel", FermentationBarrelBlock::new, Block.Properties.ofFullCopy(Blocks.BARREL).noOcclusion().setId(key("fermentation_barrel")), false);

	private final String pathName;
	private final Function<Block.Properties, Block> blockFactory;
	private final Block.Properties properties;
	private final boolean isCutout;
	private Block block;
	private boolean registered = false;

	ModBlocks(String pathName, Function<Block.Properties, Block> blockFactory, Block.Properties properties, boolean isCutout) {
		this.pathName = pathName;
		this.blockFactory = blockFactory;
		this.properties = properties;
		this.isCutout = isCutout;
	}

	public static void registerAll() {
		for (ModBlocks value : values()) {
			value.register();
		}
	}

	private void register() {
		if (!registered) {
			this.block = Registry.register(
					BuiltInRegistries.BLOCK,
					ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, this.pathName),
					this.blockFactory.apply(this.properties)
			);
		}

		this.registered = true;
	}

	private static ResourceKey<Block> key(String name) {
		return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, name));
	}

	private static Block.Properties potProps(String name) {
		return Block.Properties.ofFullCopy(Blocks.BRICKS).strength(0.5F).sound(SoundType.ANVIL).noOcclusion().setId(key(name));
	}

	private static Block.Properties trayProps(String name) {
		return Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.5F).sound(SoundType.WOOD).noOcclusion().setId(key(name));
	}

	@Environment(EnvType.CLIENT)
	public static void registerRenderLayer() {
		for (ModBlocks value : values()) {
			if (value.isCutout) {
				LOGGER.info("Registering cutout for: " + value.pathName + " (" + value.get() + ")");
				BlockRenderLayerMap.INSTANCE.putBlock(value.get(), RenderType.cutout());
			}
		}
	}

	public Block get() {
		if (block == null) {
			throw new IllegalStateException("Block " + this.name() + " not registered yet!");
		}
		return block;
	}
}

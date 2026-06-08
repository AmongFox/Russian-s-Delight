package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

import static com.amongfox.russiansdelight.RussiansDelight.LOGGER;

public enum ModBlocks {
	// Blocks
	SMALL_POT("small_pot", SmallPotBlock::new, true),

	// Soups
	BORSCHT_POT("borscht_pot", BorschtPotBlock::new, true),
	SHCHI_POT("shchi_pot", ShchiPotBlock::new, true),
	SOLYANKA_POT("solyanka_pot",SolyankaPotBlock::new, true),
	RASSOLNIK_POT("rassolnik_pot", RassolnikPotBlock::new, true),

	// Bakery
	PANCAKES_TRAY("pancakes_tray", PancakesTrayBlock::new, true),
	FISH_PIE("fish_pie", FishPieBlock::new, true),
	CABBAGE_PIES_TRAY("cabbage_pies_tray", CabbagePiesTrayBlock::new, true),
	BERRIES_PIES_TRAY("berries_pies_tray", BerriesPiesTrayBlock::new, true),

	// Crops
	WILD_CUCUMBER("wild_cucumber", () -> new WildCucumberBlock(
			MobEffects.HUNGER, 6,
			FabricBlockSettings.copyOf(Blocks.TALL_GRASS).noCollision().breakInstantly().sounds(SoundType.GRASS)
	), true);

	private final String pathName;
	private final Supplier<Block> blockSupplier;
	private final boolean isCutout;
	private Block block;
	private boolean registered = false;

	ModBlocks(String pathName, Supplier<Block> blockSupplier, boolean isCutout) {
		this.pathName = pathName;
		this.blockSupplier = blockSupplier;
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
					new ResourceLocation(RussiansDelight.MOD_ID, this.pathName),
					this.blockSupplier.get()
			);
		}

		this.registered = true;
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

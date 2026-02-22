package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

import static com.amongfox.russiansdelight.RussiansDelight.LOGGER;

public enum BlocksRegistry {
    // Столовые предметы
    SMALL_POT("small_pot", SmallPotBlock::new, true),

    // Супы
    BORSCHT_POT("borscht_pot", BorschtPotBlock::new, true),
    SHCHI_POT("shchi_pot", ShchiPotBlock::new, true),
    SOLYANKA_POT("solyanka_pot",SolyankaPotBlock::new, true),
    RASSOLNIK_POT("rassolnik_pot", RassolnikPotBlock::new, true),

    // Выпечка
    PANCAKES_TRAY("pancakes_tray", PancakesTrayBlock::new, true),
    FISH_PIE("fish_pie", FishPieBlock::new, true),
    CABBAGE_PIES_TRAY("cabbage_pies_tray", CabbagePiesTrayBlock::new, true);

    private final String pathName;
    private final Supplier<Block> blockSupplier;
    private final boolean isCutout;
    private Block block;
    private boolean registered = false;

    BlocksRegistry(String pathName, Supplier<Block> blockSupplier, boolean isCutout) {
        this.pathName = pathName;
        this.blockSupplier = blockSupplier;
        this.isCutout = isCutout;
    }

    public static void registerAll() {
        for (BlocksRegistry value : values()) {
            value.register();
        }
    }

    private void register() {
        if (!registered) {
            this.block = Registry.register(
                    Registries.BLOCK,
                    new Identifier(RussiansDelight.MOD_ID, this.pathName),
                    this.blockSupplier.get()
            );
        }

        this.registered = true;
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayer() {
        for (BlocksRegistry value : values()) {
            if (value.isCutout) {
                LOGGER.info("🎨 Registering cutout for: " + value.pathName + " (" + value.get() + ")");
                BlockRenderLayerMap.INSTANCE.putBlock(value.get(), RenderLayer.getCutout());
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

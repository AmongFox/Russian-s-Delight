package com.amongfox.russiansdelight.registry;

import com.amongfox.russiansdelight.RussiansDelight;
import com.amongfox.russiansdelight.block.entity.FermentationBarrelBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public enum ModBlockEntities {
	FERMENTATION_BARREL("fermentation_barrel", () -> FabricBlockEntityTypeBuilder
			.create(FermentationBarrelBlockEntity::new)
			.addBlock(ModBlocks.FERMENTATION_BARREL.get())
			.build());

	private final String pathName;
	private final Supplier<BlockEntityType<?>> typeSupplier;
	private BlockEntityType<?> type;
	private boolean registered = false;

	ModBlockEntities(String pathName, Supplier<BlockEntityType<?>> typeSupplier) {
		this.pathName = pathName;
		this.typeSupplier = typeSupplier;
	}

	public static void registerAll() {
		for (ModBlockEntities value : values()) {
			value.register();
		}
	}

	private void register() {
		if (!registered) {
			this.type = Registry.register(
					BuiltInRegistries.BLOCK_ENTITY_TYPE,
					ResourceLocation.fromNamespaceAndPath(RussiansDelight.MOD_ID, this.pathName),
					this.typeSupplier.get()
			);
			this.registered = true;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityType<T> get() {
		if (type == null) {
			throw new IllegalStateException("BlockEntity " + this.name() + " not registered yet!");
		}
		return (BlockEntityType<T>) type;
	}
}

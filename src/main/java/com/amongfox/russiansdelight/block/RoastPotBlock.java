package com.amongfox.russiansdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class RoastPotBlock extends AbstractPotBlock {
	public RoastPotBlock(FabricBlockSettings fabricBlockSettings) {
		super(fabricBlockSettings);
	}

	@Override
	protected int getMaxServings() {
		return 0;
	}

	@Override
	protected Item getFoodItem() {
		return null;
	}

	@Override
	protected VoxelShape getShape() {
		return null;
	}

	@Override
	protected SoundEvent getTakeServingSoundEvent() {
		return null;
	}

	@Override
	protected SoundEvent getAddServingSoundEvent() {
		return null;
	}

	@Override
	protected SoundEvent getBreakSoundEvent() {
		return null;
	}

	@Override
	protected List<ItemStack> getLeftoverDrops() {
		return null;
	}
}

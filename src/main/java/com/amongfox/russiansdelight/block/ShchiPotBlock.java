package com.amongfox.russiansdelight.block;

import com.amongfox.russiansdelight.registry.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class ShchiPotBlock extends AbstractPotBlock {
	private static final int MAX_SERVINGS = 6;
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

	public ShchiPotBlock() {
		super(FabricBlockSettings.copyOf(Blocks.BRICKS).strength(0.5F).sounds(SoundType.ANVIL).nonOpaque());
	}

	@Override
	protected int getMaxServings() {
		return MAX_SERVINGS;
	}

	@Override
	protected Item getFoodItem() {
		return ModItems.SHCHI.get();
	}

	@Override
	protected VoxelShape getShape() {
		return SHAPE;
	}

	@Override
	protected SoundEvent getTakeServingSoundEvent() {
		return SoundEvents.BUCKET_FILL;
	}

	@Override
	protected SoundEvent getAddServingSoundEvent() {
		return SoundEvents.BUCKET_EMPTY;
	}

	@Override
	protected SoundEvent getBreakSoundEvent() {
		return SoundEvents.ANVIL_BREAK;
	}

	@Override
	protected List<ItemStack> getLeftoverDrops() {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(ModItems.SMALL_POT.get(), 1));
		drops.add(new ItemStack(Items.BONE, 1));
		return drops;
	}
}

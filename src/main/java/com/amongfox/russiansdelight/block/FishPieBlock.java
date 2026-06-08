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

public class FishPieBlock extends AbstractTrayBlock {
	private static final int MAX_SERVINGS = 4;
	protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);

	public FishPieBlock() {
		super(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(0.5F).sounds(SoundType.WOOD).nonOpaque());
	}

	@Override
	protected int getMaxServings() {
		return MAX_SERVINGS;
	}

	@Override
	protected boolean getEatDirectly() {
		return true;
	}

	@Override
	protected Item getFoodItem() {
		return ModItems.PIECE_FISH_PIE.get();
	}

	@Override
	protected VoxelShape getShape() {
		return SHAPE;
	}

	@Override
	protected SoundEvent getTakeServingSoundEvent() {
		return SoundEvents.ARMOR_EQUIP_GENERIC;
	}

	@Override
	protected SoundEvent getAddServingSoundEvent() {
		return SoundEvents.ARMOR_EQUIP_GENERIC;
	}

	@Override
	protected SoundEvent getBreakSoundEvent() {
		return SoundEvents.WOOD_BREAK;
	}

	@Override
	protected List<ItemStack> getLeftoverDrops() {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(Items.BOWL, 1));
		drops.add(new ItemStack(Items.BONE, 2));
		return drops;
	}
}

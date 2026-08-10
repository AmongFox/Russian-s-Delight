package com.amongfox.russiansdelight.block;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WildBuckwheatBlock extends FlowerBlock implements BonemealableBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

	public WildBuckwheatBlock(Holder<MobEffect> suspiciousStewEffect, int effectDuration, BlockBehaviour.Properties settings) {
		super(suspiciousStewEffect, effectDuration, settings);
		FlammableBlockRegistry.getDefaultInstance().add(this, 100, 60);
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter view, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean mayPlaceOn(BlockState floor, @NotNull BlockGetter view, @NotNull BlockPos pos) {
		return floor.is(BlockTags.DIRT) || floor.is(BlockTags.SAND);
	}

	@Override
	public boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext context) {
		return false;
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level world, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return random.nextFloat() < 0.8F;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel world, @NotNull RandomSource random, BlockPos pos, @NotNull BlockState state) {
		int wildCropLimit = 10;
		for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4))) {
			if (world.getBlockState(nearbyPos).is(this)) {
				--wildCropLimit;
				if (wildCropLimit <= 0) return;
			}
		}

		BlockPos randomPos = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
		for (int k = 0; k < 4; ++k) {
			if (world.isEmptyBlock(randomPos) && state.canSurvive(world, randomPos)) {
				pos = randomPos;
			}
			randomPos = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
		}
		if (world.isEmptyBlock(randomPos) && state.canSurvive(world, randomPos)) {
			world.setBlock(randomPos, state, 2);
		}
	}
}

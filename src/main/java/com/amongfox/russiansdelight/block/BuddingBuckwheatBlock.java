package com.amongfox.russiansdelight.block;

import com.amongfox.russiansdelight.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.BuddingBushBlock;

public class BuddingBuckwheatBlock extends BuddingBushBlock implements BonemealableBlock {
	public BuddingBuckwheatBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public BlockState getPlant(BlockGetter world, BlockPos pos) {
		return ModBlocks.BUCKWHEAT_CROP.get().defaultBlockState();
	}

	public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		return pState.is(vectorwing.farmersdelight.common.registry.ModBlocks.RICH_SOIL_FARMLAND.get()) || pState.is(Blocks.FARMLAND);
	}

	public boolean canGrowPastMaxAge() {
		return true;
	}

	public void growPastMaxAge(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		level.setBlockAndUpdate(pos, ModBlocks.BUCKWHEAT_CROP.get().defaultBlockState());
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	protected int getBonemealAgeIncrease(Level level) {
		return Mth.nextInt(level.random, 1, 4);
	}

	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		int maxAge = this.getMaxAge();
		int ageGrowth = Math.min(this.getAge(state) + this.getBonemealAgeIncrease(level), 7);
		if (ageGrowth <= maxAge) {
			level.setBlockAndUpdate(pos, (BlockState) state.setValue(AGE, ageGrowth));
		} else {
			int remainingGrowth = ageGrowth - maxAge - 1;
			level.setBlockAndUpdate(pos, (BlockState) ModBlocks.BUCKWHEAT_CROP.get().defaultBlockState().setValue(BuckwheatCropBlock.AGE, remainingGrowth));
		}

	}
}

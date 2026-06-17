package com.amongfox.russiansdelight.block;

import com.amongfox.russiansdelight.block.entity.SmallCookingPotBlockEntity;
import com.amongfox.russiansdelight.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SmallCookingPotBlock extends CookingPotBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

	public SmallCookingPotBlock() {
		super(FabricBlockSettings.copyOf(Blocks.BRICKS).strength(0.5F).sounds(SoundType.ANVIL).nonOpaque());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SmallCookingPotBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
		if (level.isClientSide) {
			return BaseEntityBlock.createTickerHelper(blockEntity, ModBlockEntityTypes.SMALL_COOKING_POT_BLOCK_ENTITY, CookingPotBlockEntity::animationTick);
		}
		return BaseEntityBlock.createTickerHelper(blockEntity, ModBlockEntityTypes.SMALL_COOKING_POT_BLOCK_ENTITY, CookingPotBlockEntity::cookingTick);
	}
}

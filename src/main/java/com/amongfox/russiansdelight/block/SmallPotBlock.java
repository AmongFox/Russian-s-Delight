package com.amongfox.russiansdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SmallPotBlock extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

	public SmallPotBlock() {
		super(FabricBlockSettings.copyOf(Blocks.BRICKS).strength(0.5F).sounds(SoundType.ANVIL).nonOpaque());
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext placementContext) {
		return defaultBlockState().setValue(FACING, placementContext.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public void playerWillDestroy(Level world, BlockPos blockPos, BlockState blockState, Player player) {
		Block.popResource(world, blockPos, new ItemStack(this));
		super.playerWillDestroy(world, blockPos, blockState, player);
	}

	@Override
	public InteractionResult use(BlockState blockState, Level world, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		return InteractionResult.PASS;
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader worldView, BlockPos blockPos) {
		return worldView.getBlockState(blockPos.below()).isSolid();
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState, LevelAccessor worldAccess, BlockPos blockPos, BlockPos neighborPos) {
		return super.updateShape(blockState, direction, neighborState, worldAccess, blockPos, neighborPos);
	}

	@Override
	public boolean isPathfindable(BlockState blockState, BlockGetter blockView, BlockPos pos, PathComputationType navigationType) {
		return false;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState blockState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos blockPos) {
		return 0;
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockView, BlockPos blockPos, CollisionContext shapeContext) {
		return SHAPE;
	}
}

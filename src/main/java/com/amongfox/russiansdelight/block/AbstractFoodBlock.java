package com.amongfox.russiansdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractFoodBlock extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public AbstractFoodBlock(FabricBlockSettings fabricBlockSettings) {
		super(fabricBlockSettings);
		registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(getServingsProperty(), getMaxServings()));
	}

	public IntegerProperty getServingsProperty() {
		return IntegerProperty.create("servings", 0, getMaxServings());
	}

	protected abstract int getMaxServings();
	protected abstract Item getFoodItem();
	protected abstract VoxelShape getShape();
	protected abstract SoundEvent getTakeServingSoundEvent();
	protected abstract SoundEvent getAddServingSoundEvent();
	protected abstract SoundEvent getBreakSoundEvent();
	protected abstract List<ItemStack> getLeftoverDrops();

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext placementContext) {
		BlockState blockState = defaultBlockState().setValue(FACING, placementContext.getHorizontalDirection().getOpposite());

		ItemStack itemStack = placementContext.getItemInHand();
		if (itemStack.hasTag()) {
			CompoundTag nbt = itemStack.getTag();
			if (nbt != null && nbt.contains("servings")) {
				int savedServings = nbt.getInt("servings");
				blockState = blockState.setValue(getServingsProperty(), savedServings);
			}
		}

		return blockState;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, getServingsProperty());
	}

	@Override
	public void playerWillDestroy(Level world, BlockPos blockPos, BlockState blockState, Player player) {
		int servings = blockState.getValue(getServingsProperty());

		ItemStack blockItem = new ItemStack(this);

		if (!player.isCreative()) {
			if (servings != getMaxServings()) {
				CompoundTag nbt = new CompoundTag();
				nbt.putInt("servings", servings);
				blockItem.setTag(nbt);
			}

			Block.popResource(world, blockPos, blockItem);
		}

		super.playerWillDestroy(world, blockPos, blockState, player);
	}

	@Override
	public InteractionResult use(BlockState blockState, Level world, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		ItemStack itemStack = player.getItemInHand(hand);
		int servings = blockState.getValue(getServingsProperty());

		System.out.println("=== BLOCK INTERACTION ===");
		System.out.println("Block Class: " + this.getClass().getSimpleName());
		System.out.println("Servings: " + servings + "/" + getMaxServings());
		System.out.println("Held item: " + itemStack.getItem());
		System.out.println("Is client: " + world.isClientSide());
		System.out.println("ItemStackIsEmpty: " + itemStack.isEmpty());

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
		return blockState.getValue(getServingsProperty());
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockView, BlockPos blockPos, CollisionContext shapeContext) {
		return getShape();
	}

	public ItemStack getServingStack() {
		return new ItemStack(getFoodItem());
	}

	public InteractionResult addServing(Level world, BlockPos blockPos, BlockState blockState, Player player, InteractionHand hand) {
		int servings = blockState.getValue(getServingsProperty());

		ItemStack heldItem = player.getItemInHand(hand);

		if (heldItem.is(getFoodItem())) {
			world.setBlock(blockPos, blockState.setValue(getServingsProperty(), servings + 1), 3);
			world.playSound(null, blockPos, getAddServingSoundEvent(), SoundSource.PLAYERS, 0.8F, 0.8F);

			if (!player.getAbilities().instabuild) {
				heldItem.shrink(1);
				ItemStack bowl = new ItemStack(Items.BOWL);
				if (!player.getInventory().add(bowl)) {
					player.drop(bowl, false);
				}
			}
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	public InteractionResult eatDirectly(Level world, BlockPos blockPos, BlockState blockState, Player player, InteractionHand hand) {
		int servings = blockState.getValue(getServingsProperty());

		ItemStack serving = getServingStack();

		if (player.canEat(false)) {
			world.setBlock(blockPos, blockState.setValue(getServingsProperty(), servings - 1), 3);
			world.playSound(null, blockPos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 1.0F);

			player.getFoodData().eat(serving.getItem(), serving);

			player.swing(hand);

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	public InteractionResult pickupLeftovers(Level world, BlockPos blockPos, Player player) {
		world.playSound(null, blockPos, getBreakSoundEvent(), SoundSource.PLAYERS, 0.8F, 0.8F);
		world.destroyBlock(blockPos, false, player);

		List<ItemStack> drops = getLeftoverDrops();

		for (ItemStack stack : drops) {
			Containers.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
		}

		return InteractionResult.SUCCESS;
	}
}

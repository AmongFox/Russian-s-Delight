package com.amongfox.russiansdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractFoodBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public AbstractFoodBlock(FabricBlockSettings fabricBlockSettings) {
        super(fabricBlockSettings);
        setDefaultState((BlockState)((BlockState)this.getStateManager().getDefaultState()).with(getServingsProperty(), getMaxServings()));
    }

    public IntProperty getServingsProperty() {
        return IntProperty.of("servings", 0, getMaxServings());
    }

    // Абстрактные методы для настройки в дочерних классах
    protected abstract int getMaxServings();
    protected abstract boolean getEatDirectly();
    protected boolean getGrabDirectly() {
        return false;
    }
    protected abstract Item getFoodItem();
    protected abstract VoxelShape getShape();
    protected abstract SoundEvent getTakeServingSoundEvent();
    protected abstract SoundEvent getAddServingSoundEvent();
    protected abstract SoundEvent getBreakSoundEvent();
    protected abstract List<ItemStack> getLeftoverDrops();

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext placementContext) {
        BlockState blockState = getDefaultState().with(FACING, placementContext.getHorizontalPlayerFacing().getOpposite());

        ItemStack itemStack = placementContext.getStack();
        if (itemStack.hasNbt()) {
            NbtCompound nbt = itemStack.getNbt();
            if (nbt != null && nbt.contains("servings")) {
                int savedServings = nbt.getInt("servings");
                blockState = blockState.with(getServingsProperty(), savedServings);
            }
        }

        return blockState;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, getServingsProperty());
    }

    @Override
    public void onBreak(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player) {
        int servings = blockState.get(getServingsProperty());

        ItemStack blockItem = new ItemStack(this);

        if (!player.isCreative()) {
            if (servings != getMaxServings()) {
                NbtCompound nbt = new NbtCompound();
                nbt.putInt("servings", servings);
                blockItem.setNbt(nbt);
            }

            ItemScatterer.spawn(world, blockPos, DefaultedList.ofSize(1, blockItem));
        }

        super.onBreak(world, blockPos, blockState, player);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getStackInHand(hand);
        int servings = blockState.get(getServingsProperty());

        System.out.println("=== BLOCK INTERACTION ===");
        System.out.println("Block Class: " + this.getClass().getSimpleName());
        System.out.println("Servings: " + servings + "/" + getMaxServings());
        System.out.println("Held item: " + itemStack.getItem());
        System.out.println("Is client: " + world.isClient());
        System.out.println("ItemStackIsEmpty: " + itemStack.isEmpty());

        if (canTakeServing(itemStack, servings)) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return takeServing(world, blockPos, blockState, player, hand);
        }
        if (canAddServing(itemStack, servings)) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return addServing(world, blockPos, blockState, player, hand);
        }
        if (canGrabServing(itemStack, servings)) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return grabServing(world, blockPos, blockState, player, hand);
        }
        if (canEatDirectly(itemStack, servings)) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return eatDirectly(world, blockPos, blockState, player, hand);
        }
        if (canPickupLeftovers(itemStack, servings)) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return pickupLeftovers(world, blockPos, blockState, player, hand);
        }

        return ActionResult.PASS;
    }

    private boolean canTakeServing(ItemStack itemStack, int servings) {
        return itemStack.isOf(Items.BOWL) && servings > 0;
    }

    private boolean canAddServing(ItemStack itemStack, int servings) {
        return itemStack.isOf(getFoodItem()) && servings < getMaxServings();
    }

    protected boolean canEatDirectly(ItemStack itemStack, int servings) {
        return getEatDirectly() && itemStack.isEmpty() && servings > 0;
    }
    private boolean canPickupLeftovers(ItemStack itemStack, int servings) {
        return getGrabDirectly() && itemStack.isEmpty() && servings > 0;
    }

    private boolean canGrabServing(ItemStack itemStack, int servings) {
        return getGrabDirectly() && itemStack.isEmpty() && servings > 0;
    }

    @Override
    public boolean canPlaceAt(BlockState blockState, WorldView worldView, BlockPos blockPos) {
        return worldView.getBlockState(blockPos.down()).isSolid();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState neighborState, WorldAccess worldAccess, BlockPos blockPos, BlockPos neighborPos) {
        return super.getStateForNeighborUpdate(blockState, direction, neighborState, worldAccess, blockPos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState blockState, BlockView blockView, BlockPos pos, NavigationType navigationType) {
        return false;
    }

    @Override
    public boolean hasComparatorOutput(BlockState blockState) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState blockState, World world, BlockPos blockPos) {
        return blockState.get(getServingsProperty());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return getShape();
    }

    public ItemStack getServingStack() {
        return new ItemStack(getFoodItem());
    }

    public ActionResult takeServing(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player, Hand hand) {
        int servings = blockState.get(getServingsProperty());

        ItemStack serving = getServingStack();
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isOf(Items.BOWL)) {
            world.setBlockState(blockPos, blockState.with(getServingsProperty(), servings - 1), 3);

            world.playSound(null, blockPos, getTakeServingSoundEvent(), SoundCategory.PLAYERS, 0.8F, 0.8F);

            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
                if (!player.getInventory().insertStack(serving)) {
                    player.dropItem(serving, false);
                }
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public ActionResult addServing(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player, Hand hand) {
        int servings = blockState.get(getServingsProperty());

        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.isOf(getFoodItem())) {
            world.setBlockState(blockPos, blockState.with(getServingsProperty(), servings + 1), 3);

            world.playSound(null, blockPos, getAddServingSoundEvent(), SoundCategory.PLAYERS, 0.8F, 0.8F);

            if (!player.getAbilities().creativeMode) {
                heldItem.decrement(1);
                ItemStack bowl = new ItemStack(Items.BOWL);
                if (!player.getInventory().insertStack(bowl)) {
                    player.dropItem(bowl, false);
                }
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public ActionResult eatDirectly(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player, Hand hand) {
        System.out.println("Call eatDirectly");
        int servings = blockState.get(getServingsProperty());

        ItemStack serving = getServingStack();

        if (player.canConsume(false)) {
            world.setBlockState(blockPos, blockState.with(getServingsProperty(), servings - 1), 3);

            world.playSound(null, blockPos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.8F, 1.0F);

            player.getHungerManager().eat(serving.getItem(), serving);

            player.swingHand(hand);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public ActionResult grabServing(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player, Hand hand) {
        int servings = blockState.get(getServingsProperty());

        ItemStack serving = getServingStack();

        world.setBlockState(blockPos, blockState.with(getServingsProperty(), servings - 1), 3);
        world.playSound(null, blockPos, getTakeServingSoundEvent(), SoundCategory.PLAYERS, 0.8F, 0.8F);

        if (!player.getAbilities().creativeMode) {
            if (!player.getInventory().insertStack(serving)) {
                player.dropItem(serving, false);
            }
        }

        return ActionResult.SUCCESS;
    }

    public ActionResult pickupLeftovers(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player, Hand hand) {
        System.out.println("Call pickupLeftovers");
        world.playSound(null, blockPos, getBreakSoundEvent(), SoundCategory.PLAYERS, 0.8F, 0.8F);
        world.breakBlock(blockPos, false, player);

        List<ItemStack> drops = getLeftoverDrops();

        for (ItemStack stack : drops) {
            ItemScatterer.spawn(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
        }

        return ActionResult.SUCCESS;
    }
}
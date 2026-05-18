package com.amongfox.russiansdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractTrayBlock extends AbstractFoodBlock {
    public AbstractTrayBlock(FabricBlockSettings settings) {
        super(settings);
    }

    protected abstract boolean getEatDirectly();
    protected boolean getGrabDirectly() {
        return false;
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getStackInHand(hand);
        int servings = blockState.get(getServingsProperty());

        if (getGrabDirectly() && itemStack.isEmpty() && servings > 0) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return grabServing(world, blockPos, blockState, player);
        }

        if (itemStack.isOf(getFoodItem()) && servings < getMaxServings()) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return addServing(world, blockPos, blockState, player, hand);
        }

        if (getEatDirectly() && itemStack.isEmpty() && servings > 0) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return eatDirectly(world, blockPos, blockState, player, hand);
        }

        if (servings <= 0 && itemStack.isEmpty()) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return pickupLeftovers(world, blockPos, player);
        }

        return ActionResult.PASS;
    }

    public ActionResult grabServing(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player) {
        int servings = blockState.get(getServingsProperty());

        ItemStack serving = getServingStack();

        world.setBlockState(blockPos, blockState.with(getServingsProperty(), servings - 1), 3);
        world.playSound(null, blockPos, getTakeServingSoundEvent(), SoundCategory.PLAYERS, 0.8F, 0.8F);

        if (!player.getInventory().insertStack(serving)) {
            player.dropItem(serving, false);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult addServing(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player, Hand hand) {
        int servings = blockState.get(getServingsProperty());

        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.isOf(getFoodItem())) {
            world.setBlockState(blockPos, blockState.with(getServingsProperty(), servings + 1), 3);
            world.playSound(null, blockPos, getAddServingSoundEvent(), SoundCategory.PLAYERS, 0.8F, 0.8F);

            if (!player.getAbilities().creativeMode) heldItem.decrement(1);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}

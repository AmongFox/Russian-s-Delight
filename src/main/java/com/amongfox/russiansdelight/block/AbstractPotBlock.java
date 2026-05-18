package com.amongfox.russiansdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractPotBlock extends AbstractFoodBlock {
    public AbstractPotBlock(FabricBlockSettings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getStackInHand(hand);
        int servings = blockState.get(getServingsProperty());

        if (itemStack.isOf(Items.BOWL) && servings > 0) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return takeServing(world, blockPos, blockState, player, hand);
        }

        if (itemStack.isOf(getFoodItem()) && servings < getMaxServings()) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return addServing(world, blockPos, blockState, player, hand);
        }

        if (servings <= 0 && itemStack.isEmpty()) {
            if (world.isClient()) return ActionResult.SUCCESS;
            return pickupLeftovers(world, blockPos, player);
        }

        return ActionResult.PASS;
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
            }

            if (!player.getInventory().insertStack(serving)) {
                player.dropItem(serving, false);
            }

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
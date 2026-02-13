package com.amongfox.russiansdelight.block;

import com.amongfox.russiansdelight.registry.ItemsRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.shape.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class ShchiPotBlock extends AbstractFoodBlock {
    private static final int MAX_SERVINGS = 6;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

    public ShchiPotBlock() {
        super(FabricBlockSettings.copyOf(Blocks.BRICKS).strength(0.5F).sounds(BlockSoundGroup.ANVIL).nonOpaque());
    }

    @Override
    protected int getMaxServings() {
        return MAX_SERVINGS;
    }

    @Override
    protected boolean getEatDirectly() {
        return false;
    }

    @Override
    protected Item getFoodItem() {
        return ItemsRegistry.SHCHI.get();
    }

    @Override
    protected VoxelShape getShape() {
        return SHAPE;
    }

    @Override
    protected SoundEvent getTakeServingSoundEvent() {
        return SoundEvents.ITEM_BUCKET_FILL;
    }

    @Override
    protected SoundEvent getAddServingSoundEvent() {
        return SoundEvents.ITEM_BUCKET_EMPTY;
    }

    @Override
    protected SoundEvent getBreakSoundEvent() {
        return SoundEvents.BLOCK_ANVIL_BREAK;
    }

    @Override
    protected List<ItemStack> getLeftoverDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemsRegistry.SMALL_POT.get(), 1));
        drops.add(new ItemStack(Items.BONE, 1));
        return drops;
    }
}


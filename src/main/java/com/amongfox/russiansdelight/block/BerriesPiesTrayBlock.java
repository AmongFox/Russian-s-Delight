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

public class BerriesPiesTrayBlock extends AbstractFoodBlock {
    private static final int MAX_SERVINGS = 3;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 5.0, 15.0);
    public BerriesPiesTrayBlock() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(0.5F).sounds(BlockSoundGroup.WOOD).nonOpaque());
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
    protected boolean getGrabDirectly() {
        return true;
    }

    @Override
    protected Item getFoodItem() {
        return ItemsRegistry.BERRY_PIE.get();
    }

    @Override
    protected VoxelShape getShape() {
        return SHAPE;
    }

    @Override
    protected SoundEvent getTakeServingSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    protected SoundEvent getAddServingSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    protected SoundEvent getBreakSoundEvent() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    @Override
    protected List<ItemStack> getLeftoverDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(Items.BOWL, 1));
        return drops;
    }
}

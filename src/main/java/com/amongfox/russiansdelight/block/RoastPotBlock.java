package com.amongfox.russiansdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.shape.VoxelShape;

import java.util.List;

public class RoastPotBlock extends AbstractPotBlock {
    public RoastPotBlock(FabricBlockSettings fabricBlockSettings) {
        super(fabricBlockSettings);
    }

    @Override
    protected int getMaxServings() {
        return 0;
    }

    @Override
    protected Item getFoodItem() {
        return null;
    }

    @Override
    protected VoxelShape getShape() {
        return null;
    }

    @Override
    protected SoundEvent getTakeServingSoundEvent() {
        return null;
    }

    @Override
    protected SoundEvent getAddServingSoundEvent() {
        return null;
    }

    @Override
    protected SoundEvent getBreakSoundEvent() {
        return null;
    }

    @Override
    protected List<ItemStack> getLeftoverDrops() {
        return null;
    }
}

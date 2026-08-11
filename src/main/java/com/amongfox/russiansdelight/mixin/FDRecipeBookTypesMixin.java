package com.amongfox.russiansdelight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.refabricated.FDRecipeBookTypes;

@Mixin(value = FDRecipeBookTypes.class, remap = false)
public class FDRecipeBookTypesMixin {

    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/RecipeBookType;valueOf(Ljava/lang/String;)Lnet/minecraft/world/inventory/RecipeBookType;"
            )
    )
    private static RecipeBookType russiansDelight$wrapValueOf(String name, Operation<RecipeBookType> original) {
        try {
            return original.call(name);
        } catch (IllegalArgumentException e) {
            return RecipeBookType.CRAFTING;
        }
    }
}

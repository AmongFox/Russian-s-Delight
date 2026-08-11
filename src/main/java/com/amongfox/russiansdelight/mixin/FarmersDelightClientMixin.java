package com.amongfox.russiansdelight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.client.FarmersDelightClient;

@Mixin(value = FarmersDelightClient.class, remap = false)
public class FarmersDelightClientMixin {

    @WrapOperation(
            method = "onInitializeClient",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/recipebook/SearchRecipeBookCategory;valueOf(Ljava/lang/String;)Lnet/minecraft/client/gui/screens/recipebook/SearchRecipeBookCategory;"
            )
    )
    private SearchRecipeBookCategory russiansDelight$wrapValueOf(String name, Operation<SearchRecipeBookCategory> original) {
        try {
            return original.call(name);
        } catch (IllegalArgumentException e) {
            return SearchRecipeBookCategory.CRAFTING;
        }
    }
}

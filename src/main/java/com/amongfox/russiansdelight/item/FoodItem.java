package com.amongfox.russiansdelight.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

import java.util.function.Supplier;

public enum FoodItem {
    BORSCHT(10, 0.8f, false);

    private final Supplier<FoodComponent> food;

    FoodItem(int hunger, float saturation, Supplier<StatusEffectInstance> effect,
             float effectChance, boolean isMeat, boolean snack, boolean alwaysEdible) {
        food = () -> {
            FoodComponent.Builder builder = new FoodComponent.Builder();
            builder.hunger(hunger).saturationModifier(saturation);
            if (effect != null) {
                builder.statusEffect(effect.get(), effectChance);
            }
            if (isMeat) {
                builder.meat();
            }
            if (snack) {
                builder.snack();
            }
            if (alwaysEdible) {
                builder.alwaysEdible();
            }
            return builder.build();
        };
    }

    FoodItem(int hunger, float saturation) {
        this(hunger, saturation, null, 0.0f, false, false, false);
    }

    FoodItem(int hunger, float saturation, boolean isMeat) {
        this(hunger, saturation, null, 0.0f, isMeat, false, false);
    }

    FoodItem(int hunger, float saturation, boolean isMeat, boolean snack, boolean alwaysEdible) {
        this(hunger, saturation, null, 0.0f, isMeat, snack, alwaysEdible);
    }

    public FoodComponent getFoodComponent() {
        return food.get();
    }
}

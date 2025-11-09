package com.amongfox.russiansdelight.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;

import java.util.function.Supplier;

public enum FoodItem {
    BORSCHT(14, 12.8F, false),
    SHCHI(13, 11.2F, false),
    SOLYANKA(14, 13.5F, false),
    RASSOLNIK(12, 10.5F, false),
    OKROSHKA(10, 8.0F, false),

    PANCAKES(10, 8.0F, false);

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

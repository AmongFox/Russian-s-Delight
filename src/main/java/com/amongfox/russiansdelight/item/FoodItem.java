package com.amongfox.russiansdelight.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

public enum FoodItem {
	// Овощи
	CUCUMBER(2, 1.2F, false, true, false),

	// Супы
	BORSCHT(14, 12.8F),
	SHCHI(13, 11.2F),
	SOLYANKA(14, 13.5F),
	RASSOLNIK(12, 10.5F),
	OKROSHKA(10, 8.0F),

	// Выпечка
	CABBAGE_PIE(6,7.0F),
	BERRY_PIE(6, 6.0F),
	PANCAKES(10, 8.0F),
	PIECE_FISH_PIE(8, 9.5F),

	// ОСНОВНЫЕ БЛЮДА
	ROAST(14, 16.0F),
	PELMENI(6, 8.0F);

	private final Supplier<FoodProperties> food;

	FoodItem(int hunger, float saturation, Supplier<MobEffectInstance> effect,
			 float effectChance, boolean isMeat, boolean snack, boolean alwaysEdible) {
		food = () -> {
			FoodProperties.Builder builder = new FoodProperties.Builder();
			builder.nutrition(hunger);
			builder.saturationMod(saturation);
			if (effect != null) {
				builder.effect(effect.get(), effectChance);
			}
			if (isMeat) {
				builder.meat();
			}
			if (snack) {
				builder.fast();
			}
			if (alwaysEdible) {
				builder.alwaysEat();
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

	public FoodProperties getFoodComponent() {
		return food.get();
	}
}

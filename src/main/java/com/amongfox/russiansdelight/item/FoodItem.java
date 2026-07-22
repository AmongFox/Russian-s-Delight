package com.amongfox.russiansdelight.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

public enum FoodItem {
	// Овощи
	CUCUMBER(2, 0.4F, true, false),

	// Ингредиенты
	BUTTER(3, 0.5F),

	// Закуски
	BREAD_AND_BUTTER(8, 0.7F),

	// Супы
	BORSCHT(14, 0.8F),
	SHCHI(13, 0.75F),
	SOLYANKA(14, 0.8F),
	RASSOLNIK(12, 0.8F),
	OKROSHKA(10, 0.7F),

	// Выпечка
	CABBAGE_PIE(6, 0.6F),
	BERRY_PIE(6, 0.5F),
	PANCAKES(8, 0.6F),
	PIECE_FISH_PIE(6, 0.6F),

	// ОСНОВНЫЕ БЛЮДА
	ROAST(14, 0.8F),
	PELMENI(6, 0.8F),
	DUMPLING(1, 0.5F, true, true, false),
	SEMOLINA_PORRIDGE(10, 0.7F);

	private final Supplier<FoodProperties> food;

	FoodItem(int hunger, float saturation, Supplier<MobEffectInstance> effect,
			 float effectChance, boolean snack, boolean alwaysEdible) {
		food = () -> {
			FoodProperties.Builder builder = new FoodProperties.Builder();
			builder.nutrition(hunger);
			builder.saturationModifier(saturation);
			if (effect != null) {
				builder.effect(effect.get(), effectChance);
			}
			if (snack) {
				builder.fast();
			}
			if (alwaysEdible) {
				builder.alwaysEdible();
			}
			return builder.build();
		};
	}

	FoodItem(int hunger, float saturation) {
		this(hunger, saturation, null, 0.0f, false, false);
	}

	FoodItem(int hunger, float saturation, boolean snack, boolean alwaysEdible) {
		this(hunger, saturation, null, 0.0f, snack, alwaysEdible);
	}

	public FoodProperties getFoodComponent() {
		return food.get();
	}
}

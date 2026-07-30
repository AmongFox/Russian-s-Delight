package com.amongfox.russiansdelight.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KvassItem extends DrinkableItem {
	public KvassItem(Properties properties) {
		super(properties, false, true);
	}

	@Override
	public void affectConsumer(ItemStack stack, Level level, LivingEntity entity) {
		Iterator<MobEffectInstance> iterator = entity.getActiveEffects().iterator();

		List<MobEffect> curativeEffects = new ArrayList<>();
		while (iterator.hasNext()) {
			MobEffectInstance effect = iterator.next();
			if (!effect.getEffect().isBeneficial()) {
				curativeEffects.add(effect.getEffect());
			}
		}

		for (MobEffect effect : curativeEffects) {
			entity.removeEffect(effect);
		}
	}
}

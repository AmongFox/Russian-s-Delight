package com.amongfox.russiansdelight.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import com.amongfox.russiansdelight.registry.ModItems;

public class VodkaItem extends DrinkableItem {
	public VodkaItem(Properties properties) {
		super(properties, false, true);
	}

	@Override
	public void affectConsumer(ItemStack stack, Level level, LivingEntity entity) {
		entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
		entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0));
		entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		ItemStack result = super.finishUsingItem(stack, level, entity);

		if (stack.isEmpty()) {
			return new ItemStack(ModItems.LARGE_GLASS_BOTTLE.get());
		}

		if (entity instanceof net.minecraft.world.entity.player.Player player && !player.getAbilities().instabuild) {
			ItemStack bottle = new ItemStack(ModItems.LARGE_GLASS_BOTTLE.get());
			if (!player.getInventory().add(bottle)) {
				player.drop(bottle, false);
			}
		}

		return result;
	}
}

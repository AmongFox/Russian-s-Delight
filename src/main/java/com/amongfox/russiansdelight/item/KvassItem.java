package com.amongfox.russiansdelight.item;

import com.amongfox.russiansdelight.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KvassItem extends DrinkableItem {
	public KvassItem(Properties properties) {
		super(properties, false, true);
	}

	@Override
	public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		ItemStack result = super.finishUsingItem(stack, level, entity);

		if (stack.isEmpty()) {
			return new ItemStack(ModItems.WOODEN_MUG.get());
		}

		if (entity instanceof net.minecraft.world.entity.player.Player player && !player.getAbilities().instabuild) {
			ItemStack mug = new ItemStack(ModItems.WOODEN_MUG.get());
			if (!player.getInventory().add(mug)) {
				player.drop(mug, false);
			}
		}

		return result;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
		tooltip.add(Component.translatable("russiansdelight.tooltip.kvass").withStyle(ChatFormatting.GRAY));
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

		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 1));
	}
}

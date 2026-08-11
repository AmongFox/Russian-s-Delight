package com.amongfox.russiansdelight.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import com.amongfox.russiansdelight.registry.ModItems;

import java.util.function.Consumer;

public class VodkaItem extends ConsumableItem {
	public VodkaItem(Properties properties) {
		super(properties, false, true);
	}

	@Override
	public void affectConsumer(ItemStack stack, Level level, LivingEntity entity) {
		entity.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 600, 1));
		entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 600, 0));
		entity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 600, 0));
	}

	@Override
	public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
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

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag isAdvanced) {
		tooltip.accept(Component.translatable("russiansdelight.tooltip.vodka").withStyle(ChatFormatting.GRAY));
	}
}

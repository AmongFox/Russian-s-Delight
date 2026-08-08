package com.amongfox.russiansdelight.screen;

import com.amongfox.russiansdelight.registry.ModItems;
import com.amongfox.russiansdelight.registry.ModMenus;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FermentingBarrelMenu extends AbstractContainerMenu {
	private static final int INPUT_SLOTS = 4;
	private static final int RESULT_SLOT = 4;
	private static final int CONTAINER_SLOT = 5;
	private static final int OUTPUT_SLOT = 6;
	private static final int CONTAINER_SIZE = 7;
	private static final int PLAYER_INV_START = 7;

	private final Container container;
	private final ContainerData data;

	public FermentingBarrelMenu(int syncId, Inventory playerInventory) {
		this(syncId, playerInventory, new SimpleContainer(CONTAINER_SIZE), new SimpleContainerData(2));
	}

	public FermentingBarrelMenu(int syncId, Inventory playerInventory, Container container, ContainerData data) {
		super(ModMenus.FERMENTING_BARREL.get(), syncId);
		checkContainerSize(container, CONTAINER_SIZE);
		this.container = container;
		this.data = data;
		container.startOpen(playerInventory.player);
		this.addDataSlots(data);

		this.addSlot(new Slot(container, 0, 26, 25));
		this.addSlot(new Slot(container, 1, 44, 25));
		this.addSlot(new Slot(container, 2, 26, 43));
		this.addSlot(new Slot(container, 3, 44, 43));
		this.addSlot(new Slot(container, RESULT_SLOT, 107, 34) {
			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
				return false;
			}

			@Override
			public boolean mayPickup(@NotNull Player player) {
				return false;
			}
		});
		this.addSlot(new Slot(container, CONTAINER_SLOT, 107, 55) {
			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
				return stack.is(ModItems.WOODEN_MUG.get()) || stack.is(ModItems.LARGE_GLASS_BOTTLE.get());
			}

			@Override
			public @NotNull Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("farmersdelight", "item/empty_container_slot_bowl"));
			}
		});
		this.addSlot(new Slot(container, OUTPUT_SLOT, 129, 55) {
			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
				return false;
			}
		});

		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
			}
		}
		for (int col = 0; col < 9; ++col) {
			this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
		}
	}

	public float getProgress() {
		int total = data.get(1);
		return total <= 0 ? 0.0F : Math.min(1.0F, (float) data.get(0) / (float) total);
	}

	public int getRemainingTime() {
		int total = data.get(1);
		if (total <= 0) {
			return 0;
		}
		return Math.max(0, total - data.get(0));
	}

	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack stack = slot.getItem();
			itemStack = stack.copy();
			if (index == RESULT_SLOT) {
				return ItemStack.EMPTY;
			}
			if (index == OUTPUT_SLOT) {
				if (!this.moveItemStackTo(stack, PLAYER_INV_START, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= PLAYER_INV_START) {
				boolean isValidContainer = stack.is(ModItems.WOODEN_MUG.get()) || stack.is(ModItems.LARGE_GLASS_BOTTLE.get());
				if (isValidContainer && !this.moveItemStackTo(stack, CONTAINER_SLOT, CONTAINER_SLOT + 1, false)) {
					return ItemStack.EMPTY;
				} else if (!this.moveItemStackTo(stack, 0, INPUT_SLOTS, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stack, PLAYER_INV_START, this.slots.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (stack.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, stack);
		}
		return itemStack;
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public void removed(@NotNull Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}
}

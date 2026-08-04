package com.amongfox.russiansdelight.screen;

import com.amongfox.russiansdelight.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FermentingBarrelMenu extends AbstractContainerMenu {
	private static final int INPUT_SLOTS = 4;
	private static final int CONTAINER_SIZE = 5;
	private static final int OUTPUT_SLOT = 4;
	private static final int PLAYER_INV_START = 5;

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
		this.addSlot(new Slot(container, OUTPUT_SLOT, 107, 34) {
			@Override
			public boolean mayPlace(ItemStack stack) {
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

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			itemStack = stack.copy();
			if (index < PLAYER_INV_START) {
				if (!this.moveItemStackTo(stack, PLAYER_INV_START, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stack, 0, INPUT_SLOTS, false)) {
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
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}
}

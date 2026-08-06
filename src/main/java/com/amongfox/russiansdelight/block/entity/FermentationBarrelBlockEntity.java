package com.amongfox.russiansdelight.block.entity;

import com.amongfox.russiansdelight.block.FermentationBarrelBlock;
import com.amongfox.russiansdelight.recipe.FermentationRecipe;
import com.amongfox.russiansdelight.registry.ModBlockEntities;
import com.amongfox.russiansdelight.registry.ModParticles;
import com.amongfox.russiansdelight.registry.ModRecipeTypes;
import com.amongfox.russiansdelight.registry.ModSounds;
import com.amongfox.russiansdelight.screen.FermentingBarrelMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FermentationBarrelBlockEntity extends RandomizableContainerBlockEntity implements ContainerData {
	private static final int CONTAINER_SIZE = 5;
	private static final int INPUT_END = 4;
	private static final int OUTPUT_SLOT = 4;
	private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
	private int brewTime;
	private int brewTimeTotal;
	private int soundTimer;

	public boolean isFermenting() {
		return this.getBlockState().getValue(FermentationBarrelBlock.FERMENTING);
	}

	public FermentationBarrelBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.FERMENTATION_BARREL.get(), pos, state);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, FermentationBarrelBlockEntity blockEntity) {
		if (level.isClientSide) {
			return;
		}
		RecipeType<FermentationRecipe> recipeType = (RecipeType<FermentationRecipe>) ModRecipeTypes.FERMENTING.get();
		Optional<FermentationRecipe> recipe = level.getRecipeManager().getRecipeFor(recipeType, blockEntity, level);
		boolean fermenting = recipe.isPresent() && blockEntity.canCraft(recipe.get(), level);
		if (fermenting) {
			if (blockEntity.brewTimeTotal == 0) {
				blockEntity.brewTimeTotal = recipe.get().getDuration();
			}
			blockEntity.brewTime++;
			if (blockEntity.brewTime >= blockEntity.brewTimeTotal) {
				blockEntity.craft(recipe.get(), level);
			}
		} else {
			blockEntity.brewTime = 0;
			blockEntity.brewTimeTotal = 0;
		}
		if (state.getValue(FermentationBarrelBlock.FERMENTING) != fermenting) {
			level.setBlock(pos, state.setValue(FermentationBarrelBlock.FERMENTING, fermenting), 3);
		}
		blockEntity.setChanged();
	}

	public static void animationTick(Level level, BlockPos pos, BlockState state, FermentationBarrelBlockEntity blockEntity) {
		if (!blockEntity.isFermenting()) {
			blockEntity.soundTimer = 0;
			return;
		}
		RandomSource random = level.random;
		if (++blockEntity.soundTimer >= 100) {
			blockEntity.soundTimer = 0;
			for (int i = 0; i < 4; i++) {
				double x = pos.getX() + 0.5D + (random.nextDouble() * 0.3D - 0.15D);
				double y = pos.getY() + 0.85D;
				double z = pos.getZ() + 0.5D + (random.nextDouble() * 0.3D - 0.15D);
				level.addParticle(ModParticles.FERMENTATION_BARREL_BUBBLE.get(), x, y, z, 0.0D, 0.1D, 0.0D);
			}
			level.playLocalSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, ModSounds.FERMENTATION_BARREL_BUBBLE.get(), SoundSource.BLOCKS, 0.2F, random.nextFloat() * 0.2F + 0.9F, false);
		}
	}

	private boolean canCraft(FermentationRecipe recipe, Level level) {
		ItemStack output = this.getItem(OUTPUT_SLOT);
		if (output.isEmpty()) {
			return true;
		}
		ItemStack result = recipe.getResultItem(level.registryAccess());
		return ItemStack.isSameItem(output, result) && output.getCount() + result.getCount() <= output.getMaxStackSize();
	}

	private void craft(FermentationRecipe recipe, Level level) {
		for (Ingredient ingredient : recipe.getIngredients()) {
			for (int i = 0; i < INPUT_END; i++) {
				ItemStack slot = this.getItem(i);
				if (ingredient.test(slot)) {
					slot.shrink(1);
					break;
				}
			}
		}
		ItemStack output = this.getItem(OUTPUT_SLOT);
		ItemStack result = recipe.getResultItem(level.registryAccess());
		if (output.isEmpty()) {
			this.setItem(OUTPUT_SLOT, result.copy());
		} else {
			output.grow(result.getCount());
		}
		this.brewTime = 0;
		this.brewTimeTotal = 0;
	}

	@Override
	public int get(int index) {
		if (index == 0) {
			return this.brewTime;
		}
		if (index == 1) {
			return this.brewTimeTotal;
		}
		return 0;
	}

	@Override
	public void set(int index, int value) {
		if (index == 0) {
			this.brewTime = value;
		} else if (index == 1) {
			this.brewTimeTotal = value;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public int getContainerSize() {
		return CONTAINER_SIZE;
	}

	@Override
	protected @NotNull NonNullList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void setItems(@NotNull NonNullList<ItemStack> list) {
		this.items = list;
	}

	@Override
	protected @NotNull Component getDefaultName() {
		return Component.translatable("container.russiansdelight.fermentation_barrel");
	}

	@Override
	protected @NotNull AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory) {
		return new FermentingBarrelMenu(syncId, playerInventory, this, this);
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		}
		return player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
	}

	@Override
	public void startOpen(@NotNull Player player) {
		if (!this.remove && !player.isSpectator()) {
			FermentationBarrelBlock.setOpen(this.level, this.worldPosition, this.getBlockState(), true);
		}
	}

	@Override
	public void stopOpen(@NotNull Player player) {
		if (!this.remove && !player.isSpectator()) {
			FermentationBarrelBlock.setOpen(this.level, this.worldPosition, this.getBlockState(), false);
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		if (!this.trySaveLootTable(tag)) {
			ContainerHelper.saveAllItems(tag, this.items);
		}
		tag.putInt("BrewTime", this.brewTime);
		tag.putInt("BrewTimeTotal", this.brewTimeTotal);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) {
			ContainerHelper.loadAllItems(tag, this.items);
		}
		this.brewTime = tag.getInt("BrewTime");
		this.brewTimeTotal = tag.getInt("BrewTimeTotal");
	}
}

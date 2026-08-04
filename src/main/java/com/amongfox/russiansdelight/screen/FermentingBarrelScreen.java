package com.amongfox.russiansdelight.screen;

import com.amongfox.russiansdelight.RussiansDelight;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FermentingBarrelScreen extends AbstractContainerScreen<FermentingBarrelMenu> {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(RussiansDelight.MOD_ID, "textures/gui/fermentation_barrel_gui.png");
	private static final int SCALE_X = 64;
	private static final int SCALE_Y = 24;
	private static final int SCALE_WIDTH = 8;
	private static final int SCALE_HEIGHT = 36;
	private static final int FILLED_SCALE_X = 176;
	private static final int FILLED_SCALE_Y = 16;

	public FermentingBarrelScreen(FermentingBarrelMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		int x = this.leftPos;
		int y = this.topPos;
		guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

		int fillHeight = Math.round(this.menu.getProgress() * SCALE_HEIGHT);
		if (fillHeight > 0) {
			guiGraphics.blit(
					GUI_TEXTURE,
					x + SCALE_X,
					y + SCALE_Y + SCALE_HEIGHT - fillHeight,
					FILLED_SCALE_X,
					FILLED_SCALE_Y + SCALE_HEIGHT - fillHeight,
					SCALE_WIDTH,
					fillHeight
			);
		}
	}
}

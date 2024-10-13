package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.entity.player.PlayerInventory;

@Environment(EnvType.CLIENT)
@Mixin(HandledScreen.class)
public class ContainerScreenMixin extends Screen {

	@Shadow
	protected int backgroundWidth;
	
	@Shadow
	protected int backgroundHeight;
	
	private static ItemRenderer itemRenderer = new ItemRenderer();

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawForeground()V", shift = Shift.AFTER))
	public void clientsideEssentials_onRender(int i, int j, float f, CallbackInfo ci) {
		if (!Config.config.GRAPHICS_CONFIG.FIX_CONTAINER_LABEL_RENDERING)
		{
			return;
		}

		HandledScreen screen = ((HandledScreen) (Object) this);
		PlayerInventory selectedItem = this.minecraft.player.inventory;

		if (selectedItem != null) {
			int posX = (screen.width - this.backgroundWidth) / 2;
			int posY = (screen.height - this.backgroundHeight) / 2;

			GL11.glPushMatrix();
			GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
			Lighting.turnOn();
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(32826);
			GL11.glEnable(2896);
			GL11.glEnable(2929);

			if (selectedItem.getCursorStack() != null) {
				GL11.glTranslatef(0.0F, 0.0F, 32.0F);
				itemRenderer.renderGuiItem(this.minecraft.textRenderer, this.minecraft.textureManager, selectedItem.getCursorStack(), i - posX - 8, j - posY - 8);
				itemRenderer.renderGuiItemDecoration(this.minecraft.textRenderer, this.minecraft.textureManager, selectedItem.getCursorStack(), i - posX - 8, j - posY - 8);
			}

			GL11.glDisable(32826);
			Lighting.turnOff();
			GL11.glDisable(2896);
			GL11.glDisable(2929);
		}
	}
}

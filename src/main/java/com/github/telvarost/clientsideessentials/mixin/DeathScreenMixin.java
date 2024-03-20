package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screen.ingame.Death;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(Death.class)
public class DeathScreenMixin extends ScreenBase {

	public DeathScreenMixin() {
    }

	@Redirect(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/screen/ingame/Death;drawTextWithShadowCentred(Lnet/minecraft/client/render/TextRenderer;Ljava/lang/String;III)V",
					ordinal = 1
			)
	)
	private void clientsideEssentials_renderDeathScreenText(Death instance, TextRenderer textRenderer, String s, int i, int j, int k) {
		if (Config.config.GRAPHICS_CONFIG.FIX_DEATH_SCREEN_TEXT) {
			int currentScore = 0;
			PlayerBase player = PlayerHelper.getPlayerFromGame();

			if (null != player) {
				currentScore = player.score;
			}

			this.drawTextWithShadowCentred(textRenderer, "Score: \u00a7e" + currentScore, i, j, k);
		}
		else
		{
			this.drawTextWithShadowCentred(textRenderer, s, i, j, k);
		}
	}
}
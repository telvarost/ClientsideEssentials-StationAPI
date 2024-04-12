package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenBase;
import net.modificationstation.stationapi.api.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screen.ingame.Death;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Environment(EnvType.CLIENT)
@Mixin(Death.class)
public class DeathScreenMixin extends ScreenBase {

	public DeathScreenMixin() {
    }

	@ModifyConstant(
			method = "render",
			constant = @Constant(stringValue = "Score: &e")
	)
	private String clientsideEssentials_renderDeathScore(String def) {
		if (Config.config.GRAPHICS_CONFIG.FIX_DEATH_SCREEN_TEXT) {
			return def.replace('&', Formatting.FORMATTING_CODE_PREFIX);
		} else {
			return def;
		}
	}
}
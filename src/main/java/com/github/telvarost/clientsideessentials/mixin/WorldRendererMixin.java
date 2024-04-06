package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.level.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	// RenderGlobal.java
	@Inject(method = "method_1552", at = @At("HEAD"), cancellable = true)
	public void clientsideEssentials_cloudRenderer(float f, CallbackInfo ci) {
		if(!ModOptions.clouds) {
			ci.cancel();
		}
	}

	@Redirect(method = "method_1552", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/dimension/Dimension;getCloudHeight()F"))
	public float clientsideEssentials_changeCloudHeight(Dimension dimension) {
		return ModOptions.getCloudHeight();
	}

	@Redirect(method = "renderClouds", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/dimension/Dimension;getCloudHeight()F"))
	public float clientsideEssentials_changeFancyCloudHeight(Dimension dimension) {
		return ModOptions.getCloudHeight();
	}
}

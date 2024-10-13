package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

	@Shadow protected EntityModel model;

	@Inject(method = "renderNameTag(Lnet/minecraft/entity/LivingEntity;DDD)V", at = @At("HEAD"), cancellable = true)
	public void clientsideEssentials_method_821(LivingEntity entity, double d, double d1, double d2, CallbackInfo ci) {
		if (Config.config.GRAPHICS_CONFIG.DISABLE_ID_TAGS) {
			ci.cancel();
		}
	}
}

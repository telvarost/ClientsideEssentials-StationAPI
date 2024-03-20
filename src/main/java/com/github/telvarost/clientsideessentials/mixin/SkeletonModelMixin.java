package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.client.render.entity.model.Skeleton;
import net.minecraft.client.render.entity.model.Zombie;

@Environment(EnvType.CLIENT)
@Mixin(Zombie.class)
public class SkeletonModelMixin {
	@Inject(method = "setAngles", at = @At("TAIL"))
	public void clientsideEssentials_setAngles(float f, float f1, float f2, float f3, float f4, float f5, CallbackInfo ci) {
		if(Config.config.GRAPHICS_CONFIG.FIX_BOW_MODEL) {
			Biped model = ((Biped) (Object) this);
			if(model instanceof Skeleton) {
				model.field_623.yaw = 0.45f;
				model.field_622.yaw = -0.2f;
				model.field_623.pitch -= 0.05f;
				model.field_622.pitch -= 0.05f;
				model.field_622.roll += 0.1f;
			}			
		}
	}
}

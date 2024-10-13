package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ZombieEntityModel.class)
public class SkeletonModelMixin {
	@Inject(method = "setAngles", at = @At("TAIL"))
	public void clientsideEssentials_setAngles(float f, float f1, float f2, float f3, float f4, float f5, CallbackInfo ci) {
		if(Config.config.GRAPHICS_CONFIG.FIX_BOW_MODEL) {
			BipedEntityModel model = ((BipedEntityModel) (Object) this);
			if(model instanceof SkeletonEntityModel) {
				model.leftArm.yaw = 0.45f;
				model.rightArm.yaw = -0.2f;
				model.leftArm.pitch -= 0.05f;
				model.rightArm.pitch -= 0.05f;
				model.rightArm.roll += 0.1f;
			}			
		}
	}
}

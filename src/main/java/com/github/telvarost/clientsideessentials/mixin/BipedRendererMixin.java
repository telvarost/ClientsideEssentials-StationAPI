package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.entity.Living;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemBase;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityRenderer.class)
public class BipedRendererMixin {

	@Inject(
			method = "method_827",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/class_556;method_1862(Lnet/minecraft/entity/Living;Lnet/minecraft/item/ItemInstance;)V",
					shift = Shift.BEFORE
			)
	)
	public void clientsideEssentials_playerRendering(Living entity, float f, CallbackInfo ci) {
		if(Config.config.GRAPHICS_CONFIG.FIX_BOW_MODEL) {
			ItemInstance item = entity.getMonsterHeldItem(); // this may be wrong .getMonsterHeldItem()
			if (item != null && item.itemId == ItemBase.bow.id) {
				GL11.glRotatef(-5, 1, 0, 0);
				GL11.glTranslatef(0.2F, -0.5F, 0.2F);
			}			
		}
	}
}

package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.UndeadEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(UndeadEntityRenderer.class)
public class BipedRendererMixin {

	@Inject(
			method = "renderMore",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
					shift = Shift.BEFORE
			)
	)
	public void clientsideEssentials_playerRendering(LivingEntity entity, float f, CallbackInfo ci) {
		if(Config.config.GRAPHICS_CONFIG.FIX_BOW_MODEL) {
			ItemStack item = entity.getHeldItem(); // this may be wrong .getMonsterHeldItem()
			if (item != null && item.itemId == Item.BOW.id) {
				GL11.glRotatef(-5, 1, 0, 0);
				GL11.glTranslatef(0.2F, -0.5F, 0.2F);
			}			
		}
	}
}

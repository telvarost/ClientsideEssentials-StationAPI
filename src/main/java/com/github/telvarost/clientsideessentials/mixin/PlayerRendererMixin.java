package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin extends LivingEntityRendererMixin {

	@Shadow
	private BipedEntityModel armor1; // Armor
	@Shadow
	private BipedEntityModel armor2; // Legs

	@Inject(
			method = "renderMore(Lnet/minecraft/entity/player/PlayerEntity;F)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
					shift = Shift.BEFORE
			)
	)
	public void clientsideEssentials_playerRendering(PlayerEntity player, float f, CallbackInfo ci) {
		if (Config.config.GRAPHICS_CONFIG.FIX_BOW_MODEL) {
			ItemStack item = player.inventory.getSelectedItem();
			if (item != null && item.itemId == Item.BOW.id) {
				GL11.glTranslatef(0.0F, -0.5F, 0.0F);
			}
		}
	}

	@Inject(
			method = "render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V",
			at = @At("HEAD")
	)
	public void clientsideEssentials_render(PlayerEntity arg, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
		if (Config.config.GRAPHICS_CONFIG.FIX_LEG_ARMOR_ON_VEHICLES) {
			ItemStack stack = arg.inventory.getArmorStack(1);
			if (stack != null) {
				Item item = stack.getItem();
				if (item instanceof ArmorItem) {
					this.armor2.riding = this.model.riding;
				}
			}
		}
	}
}

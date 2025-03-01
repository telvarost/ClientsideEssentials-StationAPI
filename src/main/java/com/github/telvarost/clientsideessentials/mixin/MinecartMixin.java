package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.ModHelper;
import net.minecraft.block.RailBlock;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecartEntity.class)
public class MinecartMixin {

	private static final double EXTRA_MINECART_XZ_SIZE = 0.4;
	private static final double EXTRA_MINECART_Y_SIZE = 0.0;

	@Inject(method = "tick", at = @At("TAIL"))
	public void clientsideEssentials_tick(CallbackInfo ci) {
		MinecartEntity minecart = (MinecartEntity) (Object) this;
		int x = MathHelper.floor(minecart.x);
		int y = MathHelper.floor(minecart.y);
		int z = MathHelper.floor(minecart.z);
		int tileId = minecart.world.getBlockId(x, y, z);
		if (RailBlock.isRail(tileId)) {
			float speed = MathHelper.sqrt(minecart.velocityX * minecart.velocityX + minecart.velocityZ * minecart.velocityZ);
			float volume = 0;
			float pitch = 0;
			if (speed >= 0.01D) {
				if (minecart.passenger != null && Config.config.GRAPHICS_CONFIG.FIX_MINECART_FLICKERING) {
					minecart.boundingBox.set(minecart.boundingBox.minX - EXTRA_MINECART_XZ_SIZE, minecart.boundingBox.minY,
							minecart.boundingBox.minZ - EXTRA_MINECART_XZ_SIZE, minecart.boundingBox.maxX + EXTRA_MINECART_XZ_SIZE,
							minecart.boundingBox.maxY + EXTRA_MINECART_Y_SIZE, minecart.boundingBox.maxZ + EXTRA_MINECART_XZ_SIZE);
				}
				++minecart.age;
				pitch = ModHelper.clamp(pitch + 0.0025F, 0.0F, 1.0F);
				volume = ModHelper.lerp(ModHelper.clamp(speed, 0.0F, 0.5F), 0.0F, 0.7F);
			} else {
				volume = 0.0f;
				pitch = 0.0f;
			}

			if (speed >= 0.01D && Config.config.SOUND_CONFIG.ADD_MINECART_ROLLING_SOUND) {
				if (minecart.age % 33 == 1) {
					minecart.world.playSound(x, y, z, "minecart.base", volume, pitch);
				}
			}
		}
	}
}

package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.particle.*;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import net.minecraft.level.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

	@Shadow private Minecraft client;

	@Shadow private Level level;

	@Shadow private TextureManager textureManager;

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


	@Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
	public void addParticle(String string, double d, double e, double f, double g, double h, double i, CallbackInfo ci) {
		if (Config.config.GRAPHICS_CONFIG.DISABLE_PARTICLES) {
			ci.cancel();
		}
//		if (this.client == null || this.client.viewEntity == null || this.client.particleManager == null) {
//			return;
//		}
//		double d2 = this.client.viewEntity.x - d;
//		double d3 = this.client.viewEntity.y - e;
//		double d4 = this.client.viewEntity.z - f;
//		double d5 = 16.0;
//		if (d2 * d2 + d3 * d3 + d4 * d4 > d5 * d5) {
//			return;
//		}
//		if (string.equals("bubble")) {
//			this.client.particleManager.addParticle(new Bubble(this.level, d, e, f, g, h, i));
//		} else if (string.equals("smoke")) {
//			this.client.particleManager.addParticle(new Smoke(this.level, d, e, f, g, h, i));
//		} else if (string.equals("note")) {
//			this.client.particleManager.addParticle(new Note(this.level, d, e, f, g, h, i));
//		} else if (string.equals("portal")) {
//			this.client.particleManager.addParticle(new Portal(this.level, d, e, f, g, h, i));
//		} else if (string.equals("explode")) {
//			this.client.particleManager.addParticle(new Explosion(this.level, d, e, f, g, h, i));
//		} else if (string.equals("flame")) {
//			this.client.particleManager.addParticle(new Fire(this.level, d, e, f, g, h, i));
//		} else if (string.equals("lava")) {
//			this.client.particleManager.addParticle(new Lava(this.level, d, e, f));
//		} else if (string.equals("footstep")) {
//			this.client.particleManager.addParticle(new Footstep(this.textureManager, this.level, d, e, f));
//		} else if (string.equals("splash")) {
//			this.client.particleManager.addParticle(new Water(this.level, d, e, f, g, h, i));
//		} else if (string.equals("largesmoke")) {
//			this.client.particleManager.addParticle(new Smoke(this.level, d, e, f, g, h, i, 2.5f));
//		} else if (string.equals("reddust")) {
//			this.client.particleManager.addParticle(new Redstone(this.level, d, e, f, (float)g, (float)h, (float)i));
//		} else if (string.equals("snowballpoof")) {
//			this.client.particleManager.addParticle(new Poof(this.level, d, e, f, ItemBase.snowball));
//		} else if (string.equals("snowshovel")) {
//			this.client.particleManager.addParticle(new SnowPuff(this.level, d, e, f, g, h, i));
//		} else if (string.equals("slime")) {
//			this.client.particleManager.addParticle(new Poof(this.level, d, e, f, ItemBase.slimeball));
//		} else if (string.equals("heart")) {
//			this.client.particleManager.addParticle(new Heart(this.level, d, e, f, g, h, i));
//		}
	}
}

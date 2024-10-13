package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	/** - All credit for the cloud code in this class goes to Dany and his mod UniTweaks
	 *  See: https://github.com/DanyGames2014/UniTweaks
	 */
	@Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
	public void clientsideEssentials_cloudRenderer(float f, CallbackInfo ci) {
		if(!ModOptions.clouds) {
			ci.cancel();
		}
	}

	@Redirect(method = "renderClouds", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/Dimension;getCloudHeight()F"))
	public float clientsideEssentials_changeCloudHeight(Dimension dimension) {
		return ModOptions.getCloudHeight();
	}

	@Redirect(method = "renderFancyClouds", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/Dimension;getCloudHeight()F"))
	public float clientsideEssentials_changeFancyCloudHeight(Dimension dimension) {
		return ModOptions.getCloudHeight();
	}


	@Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
	public void addParticle(String string, double d, double e, double f, double g, double h, double i, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableAllParticles) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 1
			),
			cancellable = true
	)
	public void addParticle_WaterBubbleParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableWaterBubbleParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 1
			),
			cancellable = true
	)
	public void addParticle_FireSmokeParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableFireSmokeParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 2
			),
			cancellable = true
	)
	public void addParticle_NoteParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableNoteParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 3
			),
			cancellable = true
	)
	public void addParticle_PortalParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disablePortalParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 4
			),
			cancellable = true
	)
	public void addParticle_ExplosionParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableExplosionParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 5
			),
			cancellable = true
	)
	public void addParticle_FlameParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableFlameParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 6
			),
			cancellable = true
	)
	public void addParticle_LavaEmberParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableLavaEmberParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 7
			),
			cancellable = true
	)
	public void addParticle_FootstepParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableFootstepParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 8
			),
			cancellable = true
	)
	public void addParticle_WaterSplashParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableWaterSplashParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 9
			),
			cancellable = true
	)
	public void addParticle_LargeFireSmokeParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableLargeFireSmokeParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 10
			),
			cancellable = true
	)
	public void addParticle_RedDustParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableRedDustParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 11
			),
			cancellable = true
	)
	public void addParticle_SnowballParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableSnowballParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 12
			),
			cancellable = true
	)
	public void addParticle_SnowflakeParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableSnowShovelParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 13
			),
			cancellable = true
	)
	public void addParticle_SlimeParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableSlimeParticle) {
			ci.cancel();
		}
	}

	@Inject(
			method = "addParticle",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V",
					ordinal = 14
			),
			cancellable = true
	)
	public void addParticle_HeartParticle(String d, double e, double f, double g, double h, double i, double par7, CallbackInfo ci) {
		if (Config.config.PARTICLES_CONFIG.disableHeartParticle) {
			ci.cancel();
		}
	}
}

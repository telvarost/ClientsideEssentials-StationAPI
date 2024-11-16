package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.ModOptions;
import com.github.telvarost.clientsideessentials.PostProcess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.FloatBuffer;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class FogGameRendererMixin {

    @Shadow
    private float viewDistance;

    @Shadow private Minecraft client;

    @Shadow private FloatBuffer fogColorBuffer;

    @Shadow private float fogRed;

    @Shadow private float fogGreen;

    @Shadow private float fogBlue;

    @Shadow private boolean renderFog;

    @Inject(method = "applyFog", at = @At(value = "HEAD"))
    public void clientsideEssentials_overrideFogDensity(int f, float par2, CallbackInfo ci) {
        this.viewDistance = (256 >> this.client.options.viewDistance) * ModOptions.getFogMultiplier();
    }

    @Inject(
            method = "updateSkyAndFogColors",
            at = @At("HEAD"),
            cancellable = true
    )
    private void clientsideEssentials_method_1852(float f, CallbackInfo ci) {
        if (  (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_SLIDER)
           && (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_FOG)
        ) {
            World level = this.client.world;
            LivingEntity living = this.client.camera;
            PostProcess pp = PostProcess.instance;
            Vec3d vec3f = level.getSkyColor(this.client.camera, f);
            float red = (float) vec3f.x;
            float green = (float) vec3f.y;
            float blue = (float) vec3f.z;
            if (this.renderFog) {
                Vec3d vec3f3 = level.getCloudColor(f);
                this.fogRed = (float) vec3f3.x;
                this.fogGreen = (float) vec3f3.y;
                this.fogBlue = (float) vec3f3.z;
                red = this.fogRed;
                green = this.fogGreen;
                blue = this.fogBlue;
            } else if (living.isInFluid(Material.WATER)) {
                this.fogRed = 0.02f;
                this.fogGreen = 0.02f;
                this.fogBlue = 0.2f;
                red = this.fogRed;
                green = this.fogGreen;
                blue = this.fogBlue;
            } else if (living.isInFluid(Material.LAVA)) {
                this.fogRed = 0.6f;
                this.fogGreen = 0.1f;
                this.fogBlue = 0.0f;
                red = this.fogRed;
                green = this.fogGreen;
                blue = this.fogBlue;
            }
            if (0.0F == ModOptions.fogDensity) {
                GL11.glClearColor(red, green, blue, 0.0f);
                ci.cancel();
            }
        }
    }

    @Inject(
            method = "updateFogColorBuffer",
            at = @At("HEAD"),
            cancellable = true
    )
    private void clientsideEssentials_method_1839(float red, float green, float blue, float i, CallbackInfoReturnable<FloatBuffer> cir) {
        if (  (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_SLIDER)
           && (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_FOG)
        ) {
            PostProcess pp = PostProcess.instance;
            this.fogColorBuffer.clear();
            this.fogColorBuffer.put(pp.red(red, green, blue)).put(pp.green(red, green, blue)).put(pp.blue(red, green, blue)).put(i);
            this.fogColorBuffer.flip();
            cir.setReturnValue(this.fogColorBuffer);
        }
    }
}

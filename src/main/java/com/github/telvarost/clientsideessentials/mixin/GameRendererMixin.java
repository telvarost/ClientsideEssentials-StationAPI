package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.PostProcess;
import com.github.telvarost.clientsideessentials.events.init.KeyBindingListener;
import com.github.telvarost.clientsideessentials.ModHelper;
import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.sortme.GameRenderer;
import net.minecraft.util.maths.Vec3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.FloatBuffer;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    private float field_2350;

    @Shadow private Minecraft minecraft;

    @Shadow private FloatBuffer field_2345;

    @Shadow private float field_2346;

    @Shadow private float field_2347;

    @Shadow private float field_2348;

    @Shadow private boolean field_2330;

    @Shadow private float field_2338;

    @Shadow private float field_2339;

    @Inject(method = "method_1842", at = @At(value = "HEAD"))
    public void clientsideEssentials_overrideFogDensity(int f, float par2, CallbackInfo ci) {
        this.field_2350 = (256 >> this.minecraft.options.viewDistance) * ModOptions.getFogMultiplier();
    }

    @Unique
    public float clientsideEssentials_getFovMultiplier(float f, boolean isHand) {
        Living entity = this.minecraft.viewEntity;
        float fov = ModOptions.getFovInDegrees();

        if (isHand) {
            fov = 70F;
        }

        if (entity.isInFluid(Material.WATER)) {
            fov *= 60.0F / 70.0F;
        }

        if (ModHelper.ModHelperFields.IS_MOJANGFIX_LOADED) {
            if (Keyboard.isKeyDown(KeyBindingListener.zoom.key)) {
                fov /= 4F;
                minecraft.options.cinematicMode = true;
            } else {
                minecraft.options.cinematicMode = false;
            }
        } else {
            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                fov /= 4F;
                minecraft.options.cinematicMode = true;
            } else {
                minecraft.options.cinematicMode = false;
            }
        }

        if (entity.health <= 0) {
            float deathTimeFov = (float) entity.deathTime + f;
            fov /= (1.0F - 500F / (deathTimeFov + 500F)) * 2.0F + 1.0F;
        }

        return fov;
    }

    @Unique
    public float clientsideEssentials_getFovMultiplier(float f) {
        return clientsideEssentials_getFovMultiplier(f, false);
    }

    @Redirect(method = "method_1840", at = @At(value = "INVOKE", target = "Lnet/minecraft/sortme/GameRenderer;method_1848(F)F"))
    public float clientsideEssentials_redirectToCustomFov(GameRenderer instance, float value) {
        return clientsideEssentials_getFovMultiplier(value);
    }

    @Inject(method = "method_1845", at = @At(value = "HEAD"))
    public void clientsideEssentials_adjustHandFov(float f, int i, CallbackInfo ci) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(clientsideEssentials_getFovMultiplier(f, true), (float) minecraft.actualWidth / (float) minecraft.actualHeight, 0.05F, field_2350 * 2.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 200))
    public int clientsideEssentials_modifyPerformanceTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 120))
    public int clientsideEssentials_modifyBalancedTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 40))
    public int clientsideEssentials_modifyPowerSaverTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @Redirect(method = "method_1844", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/options/GameOptions;fpsLimit:I"))
    public int clientsideEssentials_overridePerformanceLevel(GameOptions instance){
        return ModOptions.getPerformanceLevel();
    }

    @Inject(
            method = "method_1852",
            at = @At("HEAD"),
            cancellable = true
    )
    private void method_1852(float f, CallbackInfo ci) {
        if (  (Config.config.GAMMA_CONFIG.ENABLE_GAMMA_SLIDER)
           && (Config.config.GAMMA_CONFIG.ENABLE_GAMMA_FOG)
        ) {
            Level level = this.minecraft.level;
            Living living = this.minecraft.viewEntity;
            PostProcess pp = PostProcess.instance;
            Vec3f vec3f = level.method_279(this.minecraft.viewEntity, f);
            float red = (float) vec3f.x;
            float green = (float) vec3f.y;
            float blue = (float) vec3f.z;
            if (this.field_2330) {
                Vec3f vec3f3 = level.method_282(f);
                this.field_2346 = (float) vec3f3.x;
                this.field_2347 = (float) vec3f3.y;
                this.field_2348 = (float) vec3f3.z;
                red = this.field_2346;
                green = this.field_2347;
                blue = this.field_2348;
            } else if (living.isInFluid(Material.WATER)) {
                this.field_2346 = 0.02f;
                this.field_2347 = 0.02f;
                this.field_2348 = 0.2f;
                red = this.field_2346;
                green = this.field_2347;
                blue = this.field_2348;
            } else if (living.isInFluid(Material.LAVA)) {
                this.field_2346 = 0.6f;
                this.field_2347 = 0.1f;
                this.field_2348 = 0.0f;
                red = this.field_2346;
                green = this.field_2347;
                blue = this.field_2348;
            }
            if (0.0F != ModOptions.fogDensity) {
                red = this.field_2346;
                green = this.field_2347;
                blue = this.field_2348;
            }
            GL11.glClearColor(pp.red(red, green, blue), pp.green(red, green, blue), pp.blue(red, green, blue), 0.0f);
            ci.cancel();
        }
    }

    @Inject(
            method = "method_1839",
            at = @At("HEAD"),
            cancellable = true
    )
    private void clientsideEssentials_method_1839(float red, float green, float blue, float i, CallbackInfoReturnable<FloatBuffer> cir) {
        if (  (Config.config.GAMMA_CONFIG.ENABLE_GAMMA_SLIDER)
           && (Config.config.GAMMA_CONFIG.ENABLE_GAMMA_FOG)
        ) {
            PostProcess pp = PostProcess.instance;
            this.field_2345.clear();
            this.field_2345.put(pp.red(red, green, blue)).put(pp.green(red, green, blue)).put(pp.blue(red, green, blue)).put(i);
            this.field_2345.flip();
            cir.setReturnValue(this.field_2345);
            //cir.cancel();
        }
    }
}

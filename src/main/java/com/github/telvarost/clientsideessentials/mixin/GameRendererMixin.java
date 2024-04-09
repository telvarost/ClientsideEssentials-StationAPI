package com.github.telvarost.clientsideessentials.mixin;

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
import net.minecraft.sortme.GameRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
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
    // EntityRenderer
    @Shadow
    private float field_2350;

    @Shadow private Minecraft minecraft;

    @Shadow private FloatBuffer field_2345;

    @Shadow private float field_2346;

    @Shadow private float field_2347;

    @Shadow private float field_2348;

    @Shadow private boolean field_2330;

    @Shadow protected abstract FloatBuffer method_1839(float f, float g, float h, float i);

    @Inject(method = "method_1842", at = @At(value = "HEAD"))
    public void clientsideEssentials_overrideFogDensity(int f, float par2, CallbackInfo ci) {
        this.field_2350 = (256 >> this.minecraft.options.viewDistance) * ModOptions.getFogMultiplier();
    }

    @Unique
    public float getFovMultiplier(float f, boolean isHand) {
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
    public float getFovMultiplier(float f) {
        return getFovMultiplier(f, false);
    }

    @Redirect(method = "method_1840", at = @At(value = "INVOKE", target = "Lnet/minecraft/sortme/GameRenderer;method_1848(F)F"))
    public float clientsideEssentials_redirectToCustomFov(GameRenderer instance, float value) {
        return getFovMultiplier(value);
    }

    @Inject(method = "method_1845", at = @At(value = "HEAD"))
    public void clientsideEssentials_adjustHandFov(float f, int i, CallbackInfo ci) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(getFovMultiplier(f, true), (float) minecraft.actualWidth / (float) minecraft.actualHeight, 0.05F, field_2350 * 2.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 200))
    public int modifyPerformanceTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 120))
    public int modifyBalancedTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 40))
    public int modifyPowerSaverTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @Redirect(method = "method_1844", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/options/GameOptions;fpsLimit:I"))
    public int overridePerformanceLevel(GameOptions instance){
        return ModOptions.getPerformanceLevel();
    }

//    @Inject(
//            method = "method_1842",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void method_1842(int i, float f, CallbackInfo ci) {
//        Living living = this.minecraft.viewEntity;
//        GL11.glFog(2918, this.clientsideEssentials_method_1839u(this.field_2346, this.field_2347, this.field_2348, 1.0f));
//        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
//        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//        PostProcess pp = PostProcess.instance;
//        if (this.field_2330) {
//            GL11.glFogi(2917, 2048);
//            GL11.glFogf(2914, 0.1f);
//            float f2 = 1.0f;
//            float f3 = 1.0f;
//            float f4 = 1.0f;
////            if (this.minecraft.options.anaglyph3d) {
////                float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
////                float f6 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
////                float f7 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
////                f2 = f5;
////                f3 = f6;
////                f4 = f7;
////            }
//            if (pp != null) {
//                float f5 = pp.red(f2, f3, f4);
//                float f6 = pp.green(f2, f3, f4);
//                float f7 = pp.blue(f2, f3, f4);
//                f2 = f5;
//                f3 = f6;
//                f4 = f7;
//            }
//        } else if (living.isInFluid(Material.WATER)) {
//            GL11.glFogi(2917, 2048);
//            GL11.glFogf(2914, 0.1f);
//            float f8 = 0.4f;
//            float f9 = 0.4f;
//            float f10 = 0.9f;
////            if (this.minecraft.options.anaglyph3d) {
////                float f11 = (f8 * 30.0f + f9 * 59.0f + f10 * 11.0f) / 100.0f;
////                float f12 = (f8 * 30.0f + f9 * 70.0f) / 100.0f;
////                float f13 = (f8 * 30.0f + f10 * 70.0f) / 100.0f;
////                f8 = f11;
////                f9 = f12;
////                f10 = f13;
////            }
//            if (pp != null) {
//                float f11 = pp.red(f8, f9, f10);
//                float f12 = pp.green(f8, f9, f10);
//                float f13 = pp.blue(f8, f9, f10);
//                f8 = f11;
//                f9 = f12;
//                f10 = f13;
//            }
//        } else if (living.isInFluid(Material.LAVA)) {
//            GL11.glFogi(2917, 2048);
//            GL11.glFogf(2914, 2.0f);
//            float f14 = 0.4f;
//            float f15 = 0.3f;
//            float f16 = 0.3f;
////            if (this.minecraft.options.anaglyph3d) {
////                float f17 = (f14 * 30.0f + f15 * 59.0f + f16 * 11.0f) / 100.0f;
////                float f18 = (f14 * 30.0f + f15 * 70.0f) / 100.0f;
////                float f19 = (f14 * 30.0f + f16 * 70.0f) / 100.0f;
////                f14 = f17;
////                f15 = f18;
////                f16 = f19;
////            }
//            if (pp != null) {
//                float f17 = pp.red(f14, f15, f16);
//                float f18 = pp.green(f14, f15, f16);
//                float f19 = pp.blue(f14, f15, f16);
//                f14 = f17;
//                f15 = f18;
//                f16 = f19;
//            }
//        } else {
//            GL11.glFogi(2917, 9729);
//            GL11.glFogf(2915, this.field_2350 * 0.25f);
//            GL11.glFogf(2916, this.field_2350);
//            if (i < 0) {
//                GL11.glFogf(2915, 0.0f);
//                GL11.glFogf(2916, this.field_2350 * 0.8f);
//            }
//            if (GLContext.getCapabilities().GL_NV_fog_distance) {
//                GL11.glFogi(34138, 34139);
//            }
//            if (this.minecraft.level.dimension.blocksCompassAndClock) {
//                GL11.glFogf(2915, 0.0f);
//            }
//        }
//        GL11.glEnable(2903);
//        GL11.glColorMaterial(1028, 4608);
//        ci.cancel();
//    }

    @Unique
    private FloatBuffer clientsideEssentials_method_1839u(float red, float green, float blue, float i) {
        //if (i != 0) {
        PostProcess pp = PostProcess.instance;
        this.field_2345.clear();
        this.field_2345.put(pp.red(red, green, blue)).put(pp.green(red, green, blue)).put(pp.blue(red, green, blue)).put(i);
        this.field_2345.flip();
        return this.field_2345;
        //}
    }

    @Inject(
            method = "method_1839",
            at = @At("HEAD"),
            cancellable = true
    )
    private void clientsideEssentials_method_1839(float red, float green, float blue, float i, CallbackInfoReturnable<FloatBuffer> cir) {
        //if (i != 0) {
            PostProcess pp = PostProcess.instance;
            this.field_2345.clear();
            this.field_2345.put(pp.red(red, green, blue)).put(pp.green(red, green, blue)).put(pp.blue(red, green, blue)).put(i);
            this.field_2345.flip();
            cir.setReturnValue(this.field_2345);
            cir.cancel();
        //}
    }
}

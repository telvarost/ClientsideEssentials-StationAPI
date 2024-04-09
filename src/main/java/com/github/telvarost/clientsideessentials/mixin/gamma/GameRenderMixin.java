//package com.github.telvarost.clientsideessentials.mixin.gamma;
//
//import com.github.telvarost.clientsideessentials.PostProcess;
//import com.github.telvarost.clientsideessentials.events.init.KeyBindingListener;
//import com.github.telvarost.clientsideessentials.ModHelper;
//import com.github.telvarost.clientsideessentials.ModOptions;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.block.material.Material;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.options.GameOptions;
//import net.minecraft.entity.Living;
//import net.minecraft.level.Level;
//import net.minecraft.sortme.GameRenderer;
//import net.minecraft.util.maths.Vec3f;
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GLContext;
//import org.lwjgl.util.glu.GLU;
//import org.objectweb.asm.Opcodes;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.*;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.nio.FloatBuffer;
//
///** - All credit for the code in this class goes to Dany and his mod UniTweaks
// *  See: https://github.com/DanyGames2014/UniTweaks
// */
//@Environment(EnvType.CLIENT)
//@Mixin(GameRenderer.class)
//public abstract class GameRendererMixin {
//
//    @Shadow private Minecraft minecraft;
//
//    @Shadow private FloatBuffer field_2345;
//
//    @Shadow private float field_2346;
//
//    @Shadow private float field_2347;
//
//    @Shadow private float field_2348;
//
//    @Shadow private boolean field_2330;
//
//    @Shadow private float field_2338;
//
//    @Shadow private float field_2339;
//
//    @Inject(
//            method = "method_1852",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void method_1852(float f, CallbackInfo ci) {
//        float f2;
//        float f3;
//        Level level = this.minecraft.level;
//        Living living = this.minecraft.viewEntity;
//        float f4 = 1.0f / (float)(4 - this.minecraft.options.viewDistance);
//        f4 = 1.0f - (float)Math.pow(f4, 0.25);
//        Vec3f vec3f = level.method_279(this.minecraft.viewEntity, f);
//        float f5 = (float)vec3f.x;
//        float f6 = (float)vec3f.y;
//        float f7 = (float)vec3f.z;
//        Vec3f vec3f2 = level.method_284(f);
//        this.field_2346 = (float)vec3f2.x;
//        this.field_2347 = (float)vec3f2.y;
//        this.field_2348 = 0.0f;//(float)vec3f2.z;
//        this.field_2346 += (f5 - this.field_2346) * f4;
//        this.field_2347 += (f6 - this.field_2347) * f4;
//        this.field_2348 += (f7 - this.field_2348) * 0.0f;
//        float f8 = level.getRainGradient(f);
//        if (f8 > 0.0f) {
//            f3 = 1.0f - f8 * 0.5f;
//            f2 = 1.0f - f8 * 0.4f;
//            this.field_2346 *= f3;
//            this.field_2347 *= f3;
//            this.field_2348 *= f2;
//        }
//        if ((f3 = level.getThunderGradient(f)) > 0.0f) {
//            f2 = 1.0f - f3 * 0.5f;
//            this.field_2346 *= f2;
//            this.field_2347 *= f2;
//            this.field_2348 *= f2;
//        }
//        if (this.field_2330) {
//            Vec3f vec3f3 = level.method_282(f);
//            this.field_2346 = (float)vec3f3.x;
//            this.field_2347 = (float)vec3f3.y;
//            this.field_2348 = (float)vec3f3.z;
//        } else if (living.isInFluid(Material.WATER)) {
//            this.field_2346 = 0.02f;
//            this.field_2347 = 0.02f;
//            this.field_2348 = 0.2f;
//        } else if (living.isInFluid(Material.LAVA)) {
//            this.field_2346 = 0.6f;
//            this.field_2347 = 0.1f;
//            this.field_2348 = 0.0f;
//        }
//        float f9 = this.field_2338 + (this.field_2339 - this.field_2338) * f;
//        this.field_2346 *= f9;
//        this.field_2347 *= f9;
//        this.field_2348 *= f9;
//        if (this.minecraft.options.anaglyph3d) {
//            float f10 = (this.field_2346 * 30.0f + this.field_2347 * 59.0f + this.field_2348 * 11.0f) / 100.0f;
//            float f11 = (this.field_2346 * 30.0f + this.field_2347 * 70.0f) / 100.0f;
//            float f12 = (this.field_2346 * 30.0f + this.field_2348 * 70.0f) / 100.0f;
//            this.field_2346 = f10;
//            this.field_2347 = f11;
//            this.field_2348 = f12;
//        }
//        GL11.glClearColor(this.field_2346, this.field_2347, this.field_2348, 0.0f);
//        ci.cancel();
//    }
//
//    @Inject(
//            method = "method_1839",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void clientsideEssentials_method_1839(float red, float green, float blue, float i, CallbackInfoReturnable<FloatBuffer> cir) {
//        PostProcess pp = PostProcess.instance;
//        this.field_2345.clear();
//        this.field_2345.put(pp.red(red, green, blue)).put(pp.green(red, green, blue)).put(pp.blue(red, green, blue)).put(i);
//        this.field_2345.flip();
//        cir.setReturnValue(this.field_2345);
//        cir.cancel();
//    }
//}

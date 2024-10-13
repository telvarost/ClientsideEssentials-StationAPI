package com.github.telvarost.clientsideessentials.mixin.brightness;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.PostProcess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.FloatBuffer;
import net.minecraft.client.render.platform.Lighting;

@Mixin(Lighting.class)
public class RenderHelperMixin {
    @Shadow private static FloatBuffer BUFFER;

    @Inject(
            method = "getBuffer(FFFF)Ljava/nio/FloatBuffer;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void clientsideEssentials_method_1929(float red, float green, float blue, float i, CallbackInfoReturnable<FloatBuffer> cir) {
        if (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_SLIDER) {
            if (i != 0) {
                PostProcess pp = PostProcess.instance;
                BUFFER.clear();
                BUFFER.put(pp.red(red, green, blue)).put(pp.green(red, green, blue)).put(pp.blue(red, green, blue)).put(i);
                BUFFER.flip();
                cir.setReturnValue(BUFFER);
                cir.cancel();
            }
        }
    }
}

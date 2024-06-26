package com.github.telvarost.clientsideessentials.mixin.brightness;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.PostProcess;
import net.minecraft.client.render.RenderHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.FloatBuffer;

@Mixin(RenderHelper.class)
public class RenderHelperMixin {
    @Shadow private static FloatBuffer floatBuffer;

    @Inject(
            method = "method_1929",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void clientsideEssentials_method_1929(float red, float green, float blue, float i, CallbackInfoReturnable<FloatBuffer> cir) {
        if (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_SLIDER) {
            if (i != 0) {
                PostProcess pp = PostProcess.instance;
                floatBuffer.clear();
                floatBuffer.put(pp.red(red, green, blue)).put(pp.green(red, green, blue)).put(pp.blue(red, green, blue)).put(i);
                floatBuffer.flip();
                cir.setReturnValue(floatBuffer);
                cir.cancel();
            }
        }
    }
}

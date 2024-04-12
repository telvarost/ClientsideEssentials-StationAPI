package com.github.telvarost.clientsideessentials.mixin.brightness;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.PostProcess;
import net.minecraft.client.render.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteOrder;

@Mixin(Tessellator.class)
public class TessellatorMixin {
    @Shadow private boolean hasColour;

    @Shadow private int colour;

    @Shadow private boolean disableColour;

    @Inject(
            method = "colour(IIII)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void clientsideEssentials_colour(int i, int j, int k, int l, CallbackInfo ci) {
        if (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_SLIDER) {
            if (this.disableColour) {
                return;
            }
            PostProcess pp = PostProcess.instance;

            if (i > 255) {
                i = 255;
            }
            if (j > 255) {
                j = 255;
            }
            if (k > 255) {
                k = 255;
            }
            if (l > 255) {
                l = 255;
            }
            if (i < 0) {
                i = 0;
            }
            if (j < 0) {
                j = 0;
            }
            if (k < 0) {
                k = 0;
            }
            if (l < 0) {
                l = 0;
            }
            this.hasColour = true;
            if (pp != null) {
                int r = pp.red(i, j, k);
                int g = pp.green(i, j, k);
                int b = pp.blue(i, j, k);
                i = r;
                j = g;
                k = b;
            }
            this.colour = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? l << 24 | k << 16 | j << 8 | i : i << 24 | j << 16 | k << 8 | l;
            ci.cancel();
        }
    }
}

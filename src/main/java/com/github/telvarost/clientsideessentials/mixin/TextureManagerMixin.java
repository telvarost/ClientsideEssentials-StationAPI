package com.github.telvarost.clientsideessentials.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.TexturePackManager;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.image.BufferedImage;

@Environment(value= EnvType.CLIENT)
@Mixin(TextureManager.class)
public class TextureManagerMixin {

    @Shadow public static boolean field_1245;

    @Inject(
            method = "<init>",
            at = @At("RETURN"),
            cancellable = false
    )
    public void TextureManagerMixin(TexturePackManager arg, GameOptions arg2, CallbackInfo ci) {
        //field_1245 = true;
    }

    @Inject(
            method = "bindImageToId(Ljava/awt/image/BufferedImage;I)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void bindImageToId(BufferedImage i, int par2, CallbackInfo ci) {
        field_1245 = true;
        GL30.glGenerateMipmap(par2);
    }

    @Inject(
            method = "bindImageToId([IIII)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void bindImageToId(int[] i, int j, int k, int par4, CallbackInfo ci) {
        field_1245 = false;
    }
}

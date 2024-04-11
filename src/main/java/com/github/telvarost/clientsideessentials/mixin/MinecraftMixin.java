package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;

import java.awt.*;
import java.awt.geom.AffineTransform;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftMixin {

	@Shadow public Canvas canvas;

	@Shadow public int actualWidth;

	@Shadow public int actualHeight;

	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Ljava/awt/Canvas;getWidth()I", ordinal = 1), remap = false)
	public int fixWidth(Canvas canvas){
		if(Config.config.GRAPHICS_CONFIG.FIX_SCREEN_SCALING){
			AffineTransform transform = canvas.getGraphicsConfiguration().getDefaultTransform();
			return (int) Math.ceil(canvas.getParent().getWidth() * transform.getScaleX());
		}
		return canvas.getWidth();
	}

	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Ljava/awt/Canvas;getHeight()I", ordinal = 1), remap = false)
	public int fixHeight(Canvas canvas){
		if(Config.config.GRAPHICS_CONFIG.FIX_SCREEN_SCALING){
			AffineTransform transform = canvas.getGraphicsConfiguration().getDefaultTransform();
			return (int) Math.ceil(canvas.getParent().getHeight() * transform.getScaleY());
		}
		return canvas.getHeight();
	}

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateScreenResolution(II)V"))
    public void fixCanvasSize(CallbackInfo ci){
        if(Config.config.GRAPHICS_CONFIG.FIX_SCREEN_SCALING){
            this.canvas.setBounds(0,0, this.actualWidth, this.actualHeight);
        }
    }

	@Inject(method = "openScreen", at = @At("RETURN"))
	public void openScreen(ScreenBase par1, CallbackInfo ci) {
		if(Config.config.GRAPHICS_CONFIG.FIX_SCREEN_SCALING) {
			AffineTransform transform = this.canvas.getGraphicsConfiguration().getDefaultTransform();
			int fixedWidth = (int) Math.ceil(this.canvas.getParent().getWidth() * transform.getScaleX());
			int fixedHeight = (int) Math.ceil(this.canvas.getParent().getHeight() * transform.getScaleY());
			this.canvas.setBounds(0, 0, fixedWidth, fixedHeight);
		}
	}
}

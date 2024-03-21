package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.io.File;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftMixin {

	@Shadow public Canvas canvas;

	@Shadow public int actualWidth;

	@Shadow public int actualHeight;

	@Unique private int updateDurationCounter = 0;

	@Inject(method = "loadSoundFromDir", at = @At("HEAD"))
	public void clientsideEssentials_loadSoundFromDir(String string, File file, CallbackInfo ci) {
		Minecraft mc = (Minecraft) (Object) this;
		int split = string.indexOf("/");
		String type = string.substring(0, split);
		String newSound = string.substring(split + 1);
		// For now only allow the minecart sounds, allowing all of them causes weird effects with same name sounds when the game decides which one to use
		// XXX Could always incorporate the sound3 part into the sound's name and have it accessible as sound3.random.bow for example, which would avoid the overlap with current sounds
		if (type.equalsIgnoreCase("sound3") && newSound.startsWith("minecart/")) {
			mc.soundHelper.addSound(newSound, file);
		}
	}

	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Ljava/awt/Canvas;getWidth()I"), remap = false)
	public int fixWidth(Canvas canvas){
		if(Config.config.GRAPHICS_CONFIG.FIX_SCREEN_SCALING){
			AffineTransform transform = canvas.getGraphicsConfiguration().getDefaultTransform();
			int fixedWidth = (int) Math.ceil(canvas.getParent().getWidth() * transform.getScaleX());
			if (0 < updateDurationCounter) {
				canvas.setSize(fixedWidth, this.actualHeight);
				updateDurationCounter--;
			}
			return fixedWidth;
		}
		return canvas.getWidth();
	}

	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Ljava/awt/Canvas;getHeight()I"), remap = false)
	public int fixHeight(Canvas canvas){
		if(Config.config.GRAPHICS_CONFIG.FIX_SCREEN_SCALING){
			AffineTransform transform = canvas.getGraphicsConfiguration().getDefaultTransform();
			int fixedHeight = (int) Math.ceil(canvas.getParent().getHeight() * transform.getScaleY());
			if (0 < updateDurationCounter) {
				canvas.setSize(this.actualWidth, fixedHeight);
				updateDurationCounter--;
			}
			return fixedHeight;
		}
		return canvas.getHeight();
	}

	@Inject(method = "init", at = @At("TAIL"), cancellable = true)
	public void init(CallbackInfo ci) {
		AffineTransform transform = this.canvas.getParent().getGraphicsConfiguration().getDefaultTransform();
		double scaleX = transform.getScaleX();
		double scaleY = transform.getScaleY();

		Rectangle curRec = this.canvas.getParent().getBounds();
		curRec.width = (int) (curRec.width * scaleX);
		curRec.height = (int) (curRec.height * scaleY);
		canvas.setBounds(curRec);

		Dimension curDim = this.canvas.getParent().getSize();
		curDim.width = (int) (curDim.width * scaleX);
		curDim.height = (int) (curDim.height * scaleY);
		canvas.setSize(curDim);

		this.canvas.getParent().addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				Component c = (Component)evt.getSource();
				AffineTransform transform = c.getGraphicsConfiguration().getDefaultTransform();
				double scaleX = transform.getScaleX();
				double scaleY = transform.getScaleY();

				Rectangle curRec = c.getBounds();
				curRec.width = (int) (curRec.width * scaleX);
				curRec.height = (int) (curRec.height * scaleY);
				canvas.setBounds(curRec);

				Dimension curDim = c.getSize();
				curDim.width = (int) (curDim.width * scaleX);
				curDim.height = (int) (curDim.height * scaleY);
				canvas.setMinimumSize(curDim);
				canvas.setPreferredSize(curDim);
				canvas.setMaximumSize(curDim);
				canvas.setSize(curDim);

				updateDurationCounter = 100;
			}
		});
	}
}

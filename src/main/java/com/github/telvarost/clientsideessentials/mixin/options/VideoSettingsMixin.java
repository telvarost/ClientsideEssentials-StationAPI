package com.github.telvarost.clientsideessentials.mixin.options;

import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.ScreenScaler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Environment(EnvType.CLIENT)
@Mixin(VideoOptionsScreen.class)
public class VideoSettingsMixin extends Screen {
	@Shadow
	private static Option[] VIDEO_OPTIONS;

	@Shadow private GameOptions options;

	@Shadow private Screen parent;

	static {
		VIDEO_OPTIONS = Arrays.copyOf(VIDEO_OPTIONS, VIDEO_OPTIONS.length + 4);
		VideoSettingsMixin.VIDEO_OPTIONS[VideoSettingsMixin.VIDEO_OPTIONS.length - 4] = ModOptions.brightnessOption;
		VideoSettingsMixin.VIDEO_OPTIONS[VideoSettingsMixin.VIDEO_OPTIONS.length - 3] = ModOptions.fogDensityOption;
		VideoSettingsMixin.VIDEO_OPTIONS[VideoSettingsMixin.VIDEO_OPTIONS.length - 2] = ModOptions.cloudsOption;
		VideoSettingsMixin.VIDEO_OPTIONS[VideoSettingsMixin.VIDEO_OPTIONS.length - 1] = ModOptions.cloudHeightOption;
		VideoSettingsMixin.VIDEO_OPTIONS[3] = ModOptions.fpsLimitOption;
	}

	@Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
	protected void buttonClicked(ButtonWidget arg, CallbackInfo ci) {
		if (arg.active) {
			if (arg.id < 100 && arg instanceof OptionButtonWidget) {
				this.options.setInt(((OptionButtonWidget) arg).getOption(), 1);
				arg.text = this.options.getString(Option.getById(arg.id));
			}

			if (arg.id == 200) {
				this.minecraft.options.save();
				this.minecraft.setScreen(this.parent);
			}

			if (!(arg instanceof SliderWidget)) {
				ScreenScaler var2 = new ScreenScaler(this.minecraft.options, this.minecraft.displayWidth, this.minecraft.displayHeight);
				int var3 = var2.getScaledWidth();
				int var4 = var2.getScaledHeight();
				this.init(this.minecraft, var3, var4);
			}
		}
		ci.cancel();
	}
}

package com.github.telvarost.clientsideessentials.mixin.options;

import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.menu.VideoSettings;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.client.gui.widgets.Slider;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
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
@Mixin(VideoSettings.class)
public class VideoSettingsMixin extends ScreenBase {
	@Shadow
	private static Option[] OPTIONS;

	@Shadow private GameOptions options;

	@Shadow private ScreenBase parent;

	static {
		OPTIONS = Arrays.copyOf(OPTIONS, OPTIONS.length + 4);
		VideoSettingsMixin.OPTIONS[VideoSettingsMixin.OPTIONS.length - 4] = ModOptions.brightnessOption;
		VideoSettingsMixin.OPTIONS[VideoSettingsMixin.OPTIONS.length - 3] = ModOptions.fogDensityOption;
		VideoSettingsMixin.OPTIONS[VideoSettingsMixin.OPTIONS.length - 2] = ModOptions.cloudsOption;
		VideoSettingsMixin.OPTIONS[VideoSettingsMixin.OPTIONS.length - 1] = ModOptions.cloudHeightOption;
		VideoSettingsMixin.OPTIONS[3] = ModOptions.fpsLimitOption;
	}

	@Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
	protected void buttonClicked(Button arg, CallbackInfo ci) {
		if (arg.active) {
			if (arg.id < 100 && arg instanceof OptionButton) {
				this.options.changeOption(((OptionButton) arg).getOption(), 1);
				arg.text = this.options.getTranslatedValue(Option.getById(arg.id));
			}

			if (arg.id == 200) {
				this.minecraft.options.saveOptions();
				this.minecraft.openScreen(this.parent);
			}

			if (!(arg instanceof Slider)) {
				ScreenScaler var2 = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
				int var3 = var2.getScaledWidth();
				int var4 = var2.getScaledHeight();
				this.init(this.minecraft, var3, var4);
			}
		}
		ci.cancel();
	}
}

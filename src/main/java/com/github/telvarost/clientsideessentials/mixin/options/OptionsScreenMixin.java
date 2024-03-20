package com.github.telvarost.clientsideessentials.mixin.options;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.menu.Controls;
import net.minecraft.client.gui.screen.menu.Options;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
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
@Mixin(Options.class)
public abstract class OptionsScreenMixin extends ScreenBase {

    @Shadow
    private static Option[] OPTIONS;

    @Shadow
    private GameOptions gameOptions;

    static {
        OPTIONS = Arrays.copyOf(OPTIONS, OPTIONS.length + 1);
        OptionsScreenMixin.OPTIONS[OptionsScreenMixin.OPTIONS.length - 1] = ModOptions.fovOption;
    }
}

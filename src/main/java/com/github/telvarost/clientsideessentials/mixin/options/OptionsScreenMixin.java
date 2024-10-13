package com.github.telvarost.clientsideessentials.mixin.options;

import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Environment(EnvType.CLIENT)
@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {

    @Shadow
    private static Option[] RENDER_OPTIONS;

    @Shadow
    private GameOptions options;

    static {
        RENDER_OPTIONS = Arrays.copyOf(RENDER_OPTIONS, RENDER_OPTIONS.length + 1);
        OptionsScreenMixin.RENDER_OPTIONS[OptionsScreenMixin.RENDER_OPTIONS.length - 1] = ModOptions.fovOption;
    }
}

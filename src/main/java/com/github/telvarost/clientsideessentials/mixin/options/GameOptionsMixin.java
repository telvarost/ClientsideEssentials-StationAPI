package com.github.telvarost.clientsideessentials.mixin.options;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.resource.language.TranslationStorage;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.String;
import java.io.BufferedReader;
import java.io.PrintWriter;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Environment(EnvType.CLIENT)
@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {

    @Shadow
    protected abstract float parseFloat(String string);

    @Shadow
    protected Minecraft minecraft;

    @Inject(method = "setFloat", at = @At(value = "HEAD"))
    public void clientsideEssentials_setFloat(Option option, float value, CallbackInfo ci) {
        if (option == ModOptions.brightnessOption) {
            ModOptions.brightness = value;

            if (false == Mouse.isButtonDown(0)) {
                this.minecraft.worldRenderer.reload();
                this.minecraft.textRenderer = new TextRenderer(this.minecraft.options, "/font/default.png", this.minecraft.textureManager);
                if (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_GUI) {
                    this.minecraft.textureManager.reload();
                }
            }
        }

        if (option == ModOptions.fovOption) {
            ModOptions.fov = value;
        }

        if (option == ModOptions.fogDensityOption) {
            ModOptions.fogDensity = value;
        }

        if (option == ModOptions.cloudHeightOption) {
            ModOptions.cloudHeight = value;
        }

        if (option == ModOptions.fpsLimitOption) {
            ModOptions.fpsLimit = value;
        }
    }

    @Inject(method = "setInt", at = @At(value = "HEAD"))
    public void clientsideEssentials_setInt(Option option, int value, CallbackInfo ci) {
        if (option == ModOptions.cloudsOption) {
            ModOptions.clouds = !ModOptions.clouds;
        }
    }


    @Inject(method = "getFloat", at = @At(value = "HEAD"), cancellable = true)
    public void clientsideEssentials_getFloat(Option option, CallbackInfoReturnable<Float> cir) {
        if (option == ModOptions.brightnessOption) {
            cir.setReturnValue(ModOptions.brightness);
        }

        if (option == ModOptions.fovOption) {
            cir.setReturnValue(ModOptions.fov);
        }

        if (option == ModOptions.fogDensityOption) {
            cir.setReturnValue(ModOptions.fogDensity);
        }

        if (option == ModOptions.cloudHeightOption) {
            cir.setReturnValue(ModOptions.cloudHeight);
        }

        if (option == ModOptions.fpsLimitOption) {
            cir.setReturnValue(ModOptions.fpsLimit);
        }
    }

    @Inject(method = "getBoolean", at = @At(value = "HEAD"), cancellable = true)
    public void clientsideEssentials_getBoolean(Option option, CallbackInfoReturnable<Boolean> cir) {
        if (option == ModOptions.cloudsOption) {
            cir.setReturnValue(ModOptions.clouds);
        }
    }

    @Inject(method = "getString", at = @At(value = "HEAD"), cancellable = true)
    public void clientsideEssentials_getTranslatedValue(Option option, CallbackInfoReturnable<String> cir) {
        TranslationStorage translations = TranslationStorage.getInstance();

        if (option == ModOptions.brightnessOption) {
            if (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_SLIDER) {
                float value = ModOptions.getBrightness();
                if (value == 0.0f) {
                    cir.setReturnValue(translations.get("options.clientsideessentials.brightness") + ": " + translations.get("options.clientsideessentials.brightness_min"));
                } else if (value == 0.5f) {
                    cir.setReturnValue(translations.get("options.clientsideessentials.brightness") + ": " + translations.get("options.clientsideessentials.brightness_normal"));
                } else if (value == 1.0f) {
                    cir.setReturnValue(translations.get("options.clientsideessentials.brightness") + ": " + translations.get("options.clientsideessentials.brightness_max"));
                } else {
                    cir.setReturnValue(translations.get("options.clientsideessentials.brightness") + ": " + (value * 2F) + "x");
                }
            } else {
                cir.setReturnValue(translations.get("options.clientsideessentials.brightness_disabled"));
            }
        }

        if (option == ModOptions.fovOption) {
            float value = ModOptions.fov;
            if (value == 0.0f) {
                cir.setReturnValue(translations.get("options.clientsideessentials.fov") + ": " + translations.get("options.clientsideessentials.fov_normal"));
            } else if (value == 1.0f) {
                cir.setReturnValue(translations.get("options.clientsideessentials.fov") + ": " + translations.get("options.clientsideessentials.fov_max"));
            } else {
                cir.setReturnValue(translations.get("options.clientsideessentials.fov") + ": " + ModOptions.getFovInDegrees());
            }
        }

        if (option == ModOptions.fogDensityOption) {
            float value = ModOptions.getFogDisplayValue();
            if (value == 0.0F) {
                cir.setReturnValue(translations.get("options.clientsideessentials.fog") + ": " + translations.get("options.clientsideessentials.fog_disabled"));
            } else if (value == 1.0F) {
                cir.setReturnValue(translations.get("options.clientsideessentials.fog") + ": " + translations.get("options.clientsideessentials.fog_max"));
            } else if (value == 0.5F) {
                cir.setReturnValue(translations.get("options.clientsideessentials.fog") + ": " + translations.get("options.clientsideessentials.fog_normal"));
            } else {
                cir.setReturnValue(translations.get("options.clientsideessentials.fog") + ": " + ModOptions.getFogDisplayValue() * 2F + "x");
            }
        }

        if (option == ModOptions.fpsLimitOption) {
            float value = ModOptions.getFpsLimitValue();
            if (value >= 300) {
                cir.setReturnValue(translations.get("options.clientsideessentials.fps_limit") + ": " + translations.get("options.clientsideessentials.fps_limit_max"));
            } else {
                cir.setReturnValue(translations.get("options.clientsideessentials.fps_limit") + ": " + value);
            }
        }

        if (option == ModOptions.cloudHeightOption) {
            float value = ModOptions.cloudHeight;
            String optionName = translations.get("options.clientsideessentials.cloud_height") + ": " + ModOptions.getCloudHeight();
            cir.setReturnValue(optionName);
        }

        if (option == ModOptions.cloudsOption) {
            String optionName = translations.get("options.clientsideessentials.clouds") + ": " + (ModOptions.clouds ? translations.get("options.on") : translations.get("options.off"));
            cir.setReturnValue(optionName);
        }
    }

    @Inject(
            method = "load",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;split(Ljava/lang/String;)[Ljava/lang/String;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void clientsideEssentials_load(CallbackInfo ci, BufferedReader bufferedReader, String string) {
        String[] stringArray = string.split(":");

        if (stringArray[0].equals("brightness")) {
            ModOptions.brightness = this.parseFloat(stringArray[1]);
        }

        if (stringArray[0].equals("fov")) {
            ModOptions.fov = this.parseFloat(stringArray[1]);
        }

        if (stringArray[0].equals("fog_density")) {
            ModOptions.fogDensity = this.parseFloat(stringArray[1]);
        }

        if (stringArray[0].equals("clouds")) {
            ModOptions.clouds = stringArray[1].equals("true");
        }

        if (stringArray[0].equals("cloud_height")) {
            ModOptions.cloudHeight = this.parseFloat(stringArray[1]);
        }

        if (stringArray[0].equals("fps_limit")) {
            ModOptions.fpsLimit = this.parseFloat(stringArray[1]);
        }
    }

    @Inject(
            method = "save",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/io/PrintWriter;close()V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void clientsideEssentials_saveOptions(CallbackInfo ci, PrintWriter printWriter) {
        printWriter.println("brightness:" + ModOptions.brightness);
        printWriter.println("fov:" + ModOptions.fov);
        printWriter.println("fog_density:" + ModOptions.fogDensity);
        printWriter.println("clouds:" + ModOptions.clouds);
        printWriter.println("cloud_height:" + ModOptions.cloudHeight);
        printWriter.println("fps_limit: " + ModOptions.fpsLimit);
    }
}

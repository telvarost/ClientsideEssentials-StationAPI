package com.github.telvarost.clientsideessentials.mixin.options;

import com.github.telvarost.clientsideessentials.Config;
import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

    @Inject(method = "method_1228", at = @At(value = "HEAD"))
    public void clientsideEssentials_setFloat(Option option, float value, CallbackInfo ci) {
        if (option == ModOptions.brightnessOption) {
            ModOptions.brightness = value;

            if (false == Mouse.isButtonDown(0)) {
                this.minecraft.worldRenderer.method_1537();
                this.minecraft.textRenderer = new TextRenderer(this.minecraft.options, "/font/default.png", this.minecraft.textureManager);
                if (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_GUI) {
                    this.minecraft.textureManager.reloadTexturesFromTexturePack();
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

    @Inject(method = "changeOption", at = @At(value = "HEAD"))
    public void clientsideEssentials_setBoolean(Option option, int value, CallbackInfo ci) {
        if (option == ModOptions.cloudsOption) {
            ModOptions.clouds = !ModOptions.clouds;
        }
    }


    @Inject(method = "getFloatValue", at = @At(value = "HEAD"), cancellable = true)
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

    @Inject(method = "getBooleanValue", at = @At(value = "HEAD"), cancellable = true)
    public void clientsideEssentials_getBoolean(Option option, CallbackInfoReturnable<Boolean> cir) {
        if (option == ModOptions.cloudsOption) {
            cir.setReturnValue(ModOptions.clouds);
        }
    }

    @Inject(method = "getTranslatedValue", at = @At(value = "HEAD"), cancellable = true)
    public void clientsideEssentials_getTranslatedValue(Option option, CallbackInfoReturnable<String> cir) {
        TranslationStorage translations = TranslationStorage.getInstance();

        if (option == ModOptions.brightnessOption) {
            if (Config.config.BRIGHTNESS_CONFIG.ENABLE_BRIGHTNESS_SLIDER) {
                float value = ModOptions.getBrightness();
                if (value == 0.0f) {
                    cir.setReturnValue("Brightness: Dark");
                } else if (value == 0.5f) {
                    cir.setReturnValue("Brightness: Normal");
                } else if (value == 1.0f) {
                    cir.setReturnValue("Brightness: Bright");
                } else {
                    cir.setReturnValue("Brightness: " + (value * 2F) + "x");
                }
            } else {
                cir.setReturnValue("DISABLED");
            }
        }

        if (option == ModOptions.fovOption) {
            float value = ModOptions.fov;
            if (value == 0.0f) {
                cir.setReturnValue("FOV: Normal");
            } else if (value == 1.0f) {
                cir.setReturnValue("FOV: Quake Pro");
            } else {
                cir.setReturnValue("FOV: " + ModOptions.getFovInDegrees());
            }
        }

        if (option == ModOptions.fogDensityOption) {
            float value = ModOptions.getFogDisplayValue();
            if (value == 0.0F) {
                cir.setReturnValue("Fog: Disabled");
            } else if (value == 1.0F) {
                cir.setReturnValue("Fog: Silent Hill");
            } else if (value == 0.5F) {
                cir.setReturnValue("Fog: Normal");
            } else {
                cir.setReturnValue("Fog: " + ModOptions.getFogDisplayValue() * 2F + "x");
            }
        }

        if (option == ModOptions.fpsLimitOption) {
            float value = ModOptions.getFpsLimitValue();
            if (value >= 300) {
                cir.setReturnValue(translations.translate("options.clientsideessentials:fps_limit") + ": " + translations.translate("options.clientsideessentials:fps_limit_max"));
            } else {
                cir.setReturnValue(translations.translate("options.clientsideessentials:fps_limit") + ": " + value);
            }
        }

        if (option == ModOptions.cloudHeightOption) {
            float value = ModOptions.cloudHeight;
            String optionName = "Cloud Height: " + ModOptions.getCloudHeight();
            cir.setReturnValue(optionName);
        }

        if (option == ModOptions.cloudsOption) {
            String optionName = translations.translate("options.clientsideessentials:clouds") + ": " + (ModOptions.clouds ? translations.translate("options.on") : translations.translate("options.off"));
            cir.setReturnValue(optionName);
        }
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Ljava/lang/String;split(Ljava/lang/String;)[Ljava/lang/String;"), locals = LocalCapture.CAPTURE_FAILHARD)
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

    @Inject(method = "saveOptions", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void clientsideEssentials_saveOptions(CallbackInfo ci, PrintWriter printWriter) {
        printWriter.println("brightness:" + ModOptions.brightness);
        printWriter.println("fov:" + ModOptions.fov);
        printWriter.println("fog_density:" + ModOptions.fogDensity);
        printWriter.println("clouds:" + ModOptions.clouds);
        printWriter.println("cloud_height:" + ModOptions.cloudHeight);
        printWriter.println("fps_limit: " + ModOptions.fpsLimit);
    }
}

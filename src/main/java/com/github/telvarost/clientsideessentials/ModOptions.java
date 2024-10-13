package com.github.telvarost.clientsideessentials;

import net.minecraft.client.option.Option;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
public class ModOptions {
    public static Option fogDensityOption;
    public static Option cloudsOption;
    public static Option fovOption;
    public static Option brightnessOption;
    public static Option cloudHeightOption;
    public static Option fpsLimitOption;

    // FPS Limit
    public static float fpsLimit = 0.4F;

    public static int getFpsLimitValue() {
        return (int) (Math.floor(fpsLimit * 59F) * 5) + 5;
    }

    public static int getPerformanceLevel() {
        if (getFpsLimitValue() >= 300) {
            return 0;
        } else {
            return 2;
        }
    }

    // Clouds
    public static boolean clouds = true;
    public static float cloudHeight;

    public static float getCloudHeight() {
        return Math.round(108 + cloudHeight * 148);
    }

    // FOV
    public static float fov;

    public static int getFovInDegrees() {
        return Math.round(70.0f + fov * 40.0f);
    }

    // Brightness
    public static float brightness = 0.5F;

    public static float getBrightness() {
        return (float) Math.round(brightness * 20) / 20;
    }

    // Fog Density
    public static float fogDensity = 0.5F;

    public static float getFogMultiplier() {
        if (fogDensity == 0F) {
            return 100F;
        } else {
            return (1F - Math.min(getFogDisplayValue(), 0.9F)) * 2F;
        }
    }

    public static float getFogDisplayValue() {
        return (float) Math.round(fogDensity * 20) / 20;
    }
}

package com.github.telvarost.clientsideessentials.mixin.options;

import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Environment(EnvType.CLIENT)
@Mixin(Option.class)
public abstract class OptionMixin {
    @Shadow
    @Final
    @Mutable
    private static Option[] field_1113;

    @Invoker(value = "<init>")
    private static Option newOption(String internalName, int internalId, String translationKey, boolean slider, boolean toggle) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = 179, target = "Lnet/minecraft/client/options/Option;field_1113:[Lnet/minecraft/client/options/Option;", shift = At.Shift.AFTER))
    private static void clientsideEssentials_addOptions(CallbackInfo ci) {
        ArrayList<Option> options = new ArrayList<Option>(Arrays.asList(field_1113));
        Option last = options.get(options.size() - 1);

        Option GAMMA;
        ModOptions.gammaOption = GAMMA = OptionMixin.newOption("GAMMA", last.ordinal(), "options.gamma", true, false);
        options.add(GAMMA);

        Option FOV;
        ModOptions.fovOption = FOV = OptionMixin.newOption("FOV", last.ordinal(), "options.fov", true, false);
        options.add(FOV);

        Option FOG_DENSITY;
        ModOptions.fogDensityOption = FOG_DENSITY = OptionMixin.newOption("FOG_DENSITY", last.ordinal() + 1, "options.fog_density", true, false);
        options.add(FOG_DENSITY);

        Option CLOUDS;
        ModOptions.cloudsOption = CLOUDS = OptionMixin.newOption("CLOUDS", last.ordinal() + 2, "options.clouds", false, true);
        options.add(CLOUDS);

        Option CLOUD_HEIGHT;
        ModOptions.cloudHeightOption = CLOUD_HEIGHT = OptionMixin.newOption("CLOUD_HEIGHT", last.ordinal() + 3, "options.cloud_height", true, false);
        options.add(CLOUD_HEIGHT);

        Option FPS_LIMIT;
        ModOptions.fpsLimitOption = FPS_LIMIT = OptionMixin.newOption("FPS_LIMIT", last.ordinal()+4, "options.fps_limit", true, false);
        options.add(FPS_LIMIT);

        field_1113 = options.toArray(new Option[0]);
    }
}

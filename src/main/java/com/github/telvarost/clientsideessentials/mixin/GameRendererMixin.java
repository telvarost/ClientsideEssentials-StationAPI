package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.events.init.KeyBindingListener;
import com.github.telvarost.clientsideessentials.ModHelper;
import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    private float viewDistance;

    @Shadow
    private Minecraft client;

    @Inject(
            method = "renderWorld",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/client/render/GameRenderer;viewDistance:F",
                    shift = At.Shift.AFTER
            )
    )
    public void clientsideEssentials_overrideFogDensity(float tickDelta, int eye, CallbackInfo ci) {
        this.viewDistance = (256 >> this.client.options.viewDistance) * ModOptions.getFogMultiplier();
    }

    @Unique
    public float clientsideEssentials_getFovMultiplier(float f, boolean isHand) {
        LivingEntity entity = this.client.camera;
        float fov = ModOptions.getFovInDegrees();

        if (isHand) {
            fov = 70F;
        }

        if (entity.isInFluid(Material.WATER)) {
            fov *= 60.0F / 70.0F;
        }

        if (ModHelper.ModHelperFields.IS_MOJANGFIX_LOADED) {
            if (Keyboard.isKeyDown(KeyBindingListener.zoom.code)) {
                fov /= 4F;
                client.options.cinematicMode = true;
            } else {
                client.options.cinematicMode = false;
            }
        } else {
            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                fov /= 4F;
                client.options.cinematicMode = true;
            } else {
                client.options.cinematicMode = false;
            }
        }

        if (entity.health <= 0) {
            float deathTimeFov = (float) entity.deathTime + f;
            fov /= (1.0F - 500F / (deathTimeFov + 500F)) * 2.0F + 1.0F;
        }

        return fov;
    }

    @Unique
    public float clientsideEssentials_getFovMultiplier(float f) {
        return clientsideEssentials_getFovMultiplier(f, false);
    }

    @Redirect(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getFov(F)F"))
    public float clientsideEssentials_redirectToCustomFov(GameRenderer instance, float value) {
        return clientsideEssentials_getFovMultiplier(value);
    }

    @Inject(method = "renderFirstPersonHand", at = @At(value = "HEAD"))
    public void clientsideEssentials_adjustHandFov(float f, int i, CallbackInfo ci) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(clientsideEssentials_getFovMultiplier(f, true), (float) client.displayWidth / (float) client.displayHeight, 0.05F, viewDistance * 2.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @ModifyConstant(method = "onFrameUpdate", constant = @Constant(intValue = 200))
    public int clientsideEssentials_modifyPerformanceTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "onFrameUpdate", constant = @Constant(intValue = 120))
    public int clientsideEssentials_modifyBalancedTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "onFrameUpdate", constant = @Constant(intValue = 40))
    public int clientsideEssentials_modifyPowerSaverTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @Redirect(method = "onFrameUpdate", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/option/GameOptions;fpsLimit:I"))
    public int clientsideEssentials_overridePerformanceLevel(GameOptions instance){
        return ModOptions.getPerformanceLevel();
    }
}

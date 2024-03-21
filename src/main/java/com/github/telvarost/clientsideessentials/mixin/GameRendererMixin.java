package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.ModHelper;
import com.github.telvarost.clientsideessentials.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.entity.Living;
import net.minecraft.sortme.GameRenderer;
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
public class GameRendererMixin {
    @Shadow
    private float field_2350;

    @Shadow private Minecraft minecraft;

    @Inject(method = "method_1842", at = @At(value = "HEAD"))
    public void clientsideEssentials_overrideFogDensity(int f, float par2, CallbackInfo ci) {
        this.field_2350 = (256 >> this.minecraft.options.viewDistance) * ModOptions.getFogMultiplier();
    }

    @Unique
    public float getFovMultiplier(float f, boolean isHand) {
        Living entity = this.minecraft.viewEntity;
        float fov = ModOptions.getFovInDegrees();

        if (isHand) {
            fov = 70F;
        }

        if (entity.isInFluid(Material.WATER)) {
            fov *= 60.0F / 70.0F;
        }

        // Change to keybind
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            fov /= 4F;
            minecraft.options.cinematicMode = true;
        } else {
            minecraft.options.cinematicMode = false;
        }

        if (entity.health <= 0) {
            float deathTimeFov = (float) entity.deathTime + f;
            fov /= (1.0F - 500F / (deathTimeFov + 500F)) * 2.0F + 1.0F;
        }

        return fov;
    }

    @Unique
    public float getFovMultiplier(float f) {
        return getFovMultiplier(f, false);
    }

    @Redirect(method = "method_1840", at = @At(value = "INVOKE", target = "Lnet/minecraft/sortme/GameRenderer;method_1848(F)F"))
    public float clientsideEssentials_redirectToCustomFov(GameRenderer instance, float value) {
        return getFovMultiplier(value);
    }

    @Inject(method = "method_1845", at = @At(value = "HEAD"))
    public void clientsideEssentials_adjustHandFov(float f, int i, CallbackInfo ci) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(getFovMultiplier(f, true), (float) minecraft.actualWidth / (float) minecraft.actualHeight, 0.05F, field_2350 * 2.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 200))
    public int modifyPerformanceTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 120))
    public int modifyBalancedTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "method_1844", constant = @Constant(intValue = 40))
    public int modifyPowerSaverTargetFps(int constant){
        return ModOptions.getFpsLimitValue();
    }

    @Redirect(method = "method_1844", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/options/GameOptions;fpsLimit:I"))
    public int overridePerformanceLevel(GameOptions instance){
        return ModOptions.getPerformanceLevel();
    }
}

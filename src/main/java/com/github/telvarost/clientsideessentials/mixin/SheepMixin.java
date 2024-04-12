package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.minecraft.entity.animal.AnimalBase;
import net.minecraft.entity.animal.Sheep;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Mixin(Sheep.class)
public class SheepMixin extends AnimalBase {
    public SheepMixin(Level arg) {
        super(arg);
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/animal/Sheep;setSheared(Z)V"))
    public void snipSoundOnShear(PlayerBase player, CallbackInfoReturnable<Boolean> cir){
        if(Config.config.SOUND_CONFIG.ADD_SHEEP_SHEAR_SOUND){
            player.level.playSound(player, "clientsideessentials:entity.sheep.shear", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
        }
    }
}
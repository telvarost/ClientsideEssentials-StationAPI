package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Mixin(SheepEntity.class)
public class SheepMixin extends AnimalEntity {
    public SheepMixin(World arg) {
        super(arg);
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;setSheared(Z)V"))
    public void snipSoundOnShear(PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        if(Config.config.SOUND_CONFIG.ADD_SHEEP_SHEAR_SOUND){
            player.world.playSound(player, "clientsideessentials:entity.sheep.shear", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
    }
}
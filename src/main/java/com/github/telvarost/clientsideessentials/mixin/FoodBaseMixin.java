package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.food.FoodBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Mixin(FoodBase.class)
public class FoodBaseMixin {
    @Shadow private int healAmount;
    @Unique
    private static final Random random = new Random();

    @Inject(method = "use", at = @At(value = "HEAD"))
    public void clientsideEssentials_burpOnEat(ItemInstance stack, Level level, PlayerBase user, CallbackInfoReturnable<ItemInstance> cir){
        if(Config.config.SOUND_CONFIG.ADD_FOOD_EAT_SOUND){
            level.playSound(user, "random.eat", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }

        if(Config.config.SOUND_CONFIG.ADD_FOOD_BURP_SOUND) {
            if (20 < (this.healAmount + user.health)) {
                level.playSound(user, "random.burp", 0.5F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }
}

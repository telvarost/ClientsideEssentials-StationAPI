package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.animal.AnimalBase;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.monster.MonsterBase;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Living.class)
public abstract class LivingMixin extends EntityBase {

    public LivingMixin(Level arg) {
        super(arg);
    }


    @Inject(method = "onKilledBy", at = @At("HEAD"), cancellable = true)
    public void onKilledBy(EntityBase entity, CallbackInfo ci) {
        if (  (Config.config.SCORE_CONFIG.ADD_SCORE_ON_MONSTER_KILLED)
           || (Config.config.SCORE_CONFIG.ADD_SCORE_ON_PASSIVE_KILLED)
        ) {
            if (entity instanceof PlayerBase)
            {
                Living instance = ((Living) (Object)this);

                if (  (Config.config.SCORE_CONFIG.ADD_SCORE_ON_MONSTER_KILLED)
                   && (  (instance instanceof MonsterBase)
                      || (instance instanceof Ghast)
                      || (instance instanceof Slime)
                      )
                ) {
                    ((PlayerBase)entity).score++;
                }

                if (  (Config.config.SCORE_CONFIG.ADD_SCORE_ON_PASSIVE_KILLED)
                   && (instance instanceof AnimalBase)
                ) {
                    ((PlayerBase)entity).score++;
                }
            }
        }
    }
}

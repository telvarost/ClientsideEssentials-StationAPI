package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import java.util.Random;

import net.minecraft.entity.player.PlayerBase;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.EntityBase;
import net.minecraft.item.ItemInstance;

@Mixin(ItemInstance.class)
public class ItemInstanceMixin {

	@Unique
	private static final Random random = new Random();

    @Inject(
            method = "applyDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerBase;increaseStat(Lnet/minecraft/stat/Stat;I)V",
                    shift = At.Shift.AFTER
            )
    )
    public void clientsideEssentials_applyDamage(int i, EntityBase entityBase, CallbackInfo ci) {
		if(Config.config.SOUND_CONFIG.ADD_ITEM_BREAK_SOUND) {
			PlayerBase player = PlayerHelper.getPlayerFromGame();

			if (null != player)
			{
				player.level.playSound(player, "random.break", 0.5f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}
		}
	}
}

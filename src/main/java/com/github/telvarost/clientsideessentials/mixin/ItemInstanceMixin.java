package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemInstanceMixin {

	@Unique
	private static final Random random = new Random();

    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/stat/Stat;I)V",
                    shift = At.Shift.AFTER
            )
    )
    public void clientsideEssentials_applyDamage(int i, Entity entityBase, CallbackInfo ci) {
		if(Config.config.SOUND_CONFIG.ADD_ITEM_BREAK_SOUND) {
			PlayerEntity player = PlayerHelper.getPlayerFromGame();

			if (null != player)
			{
				player.world.playSound(player, "random.break", 0.5f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}
		}
	}
}

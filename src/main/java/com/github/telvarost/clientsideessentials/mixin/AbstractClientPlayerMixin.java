package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;

import com.github.telvarost.clientsideessentials.events.init.KeyBindingListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.container.DoubleChest;
import net.minecraft.client.util.Session;
import net.minecraft.container.Chest;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin extends PlayerBase {

    @Shadow protected Minecraft minecraft;

    public AbstractClientPlayerMixin(Minecraft minecraft, Level arg, Session arg2, int i) {
        super(arg);
    }

    public void method_494() {
        /** - Do nothing */
    }

    @Inject(method = "method_136", at = @At("TAIL"))
    public void clientsideEssentials_method_136(int key, boolean state, CallbackInfo ci) {
        PlayerBase player = (PlayerBase) (Object) this;

		if (  (Config.config.SHIFT_EXIT_VEHICLES)
		   && (player.vehicle != null)
		   && (state)
		   && (key == KeyBindingListener.dismount.key)
		) {
			player.startRiding(null);
		}
    }

    @Inject(method = "openChestScreen", at = @At("TAIL"))
	public void clientsideEssentials_openChestScreen(InventoryBase arg, CallbackInfo ci) {
		this.minecraft.openScreen(new DoubleChest(this.inventory, arg));

		if(Config.config.SOUND_CONFIG.ADD_CHEST_OPEN_SOUND) {
			PlayerBase player = (PlayerBase) (Object) this;
			player.level.playSound(player, "random.chestopen", 0.3f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
		}
	}

	@Inject(method = "closeContainer", at = @At("HEAD"))
	public void clientsideEssentials_closeContainer(CallbackInfo ci) {
		if(Config.config.SOUND_CONFIG.ADD_CHEST_CLOSE_SOUND) {
			PlayerBase player = (PlayerBase) (Object) this;
			if(player.container instanceof Chest) {
				player.level.playSound(player, "random.chestclosed", 0.3f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}
		}
	}
}

package com.github.telvarost.clientsideessentials.mixin;

import com.github.telvarost.clientsideessentials.Config;

import com.github.telvarost.clientsideessentials.events.init.KeyBindingListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ingame.DoubleChestScreen;
import net.minecraft.client.util.Session;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class AbstractClientPlayerMixin extends PlayerEntity {

    @Shadow protected Minecraft minecraft;

    public AbstractClientPlayerMixin(Minecraft minecraft, World arg, Session arg2, int i) {
        super(arg);
    }

    public void spawn() {
        /** - Do nothing */
    }

    @Inject(method = "updateKey", at = @At("TAIL"))
    public void clientsideEssentials_updateKey(int key, boolean state, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

		if (  (Config.config.SHIFT_EXIT_VEHICLES)
		   && (player.vehicle != null)
		   && (state)
		   && (key == KeyBindingListener.dismount.code)
		) {
			player.setVehicle(null);
		}
    }

    @Inject(method = "openChestScreen", at = @At("TAIL"))
	public void clientsideEssentials_openChestScreen(Inventory arg, CallbackInfo ci) {
		this.minecraft.setScreen(new DoubleChestScreen(this.inventory, arg));

		if(Config.config.SOUND_CONFIG.ADD_CHEST_OPEN_SOUND) {
			PlayerEntity player = (PlayerEntity) (Object) this;
			player.world.playSound(player, "random.chestopen", 0.3f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
		}
	}

	@Inject(method = "closeHandledScreen", at = @At("HEAD"))
	public void clientsideEssentials_closeContainer(CallbackInfo ci) {
		if(Config.config.SOUND_CONFIG.ADD_CHEST_CLOSE_SOUND) {
			PlayerEntity player = (PlayerEntity) (Object) this;
			if(player.currentScreenHandler instanceof GenericContainerScreenHandler) {
				player.world.playSound(player, "random.chestclosed", 0.3f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			}
		}
	}
}

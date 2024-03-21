package com.github.telvarost.clientsideessentials.events;

import com.github.telvarost.clientsideessentials.ModHelper;
import com.github.telvarost.clientsideessentials.events.init.KeyBindingListener;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import org.lwjgl.input.Keyboard;

public class KeyPressedListener {
    Minecraft minecraft = null;

    @EventListener
    public void keyPress(KeyStateChangedEvent event) {
        if (ModHelper.ModHelperFields.IS_MOJANGFIX_LOADED) {
            if (minecraft == null) {
                minecraft = ((Minecraft) FabricLoader.getInstance().getGameInstance());
            }

            if (Keyboard.getEventKeyState() && minecraft.currentScreen == null) {
                // Hotbar Slots
                if (minecraft.currentScreen == null) {
                    if (Keyboard.isKeyDown(KeyBindingListener.hotbar1.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 0;
                    } else if (Keyboard.isKeyDown(KeyBindingListener.hotbar2.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 1;
                    } else if (Keyboard.isKeyDown(KeyBindingListener.hotbar3.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 2;
                    } else if (Keyboard.isKeyDown(KeyBindingListener.hotbar4.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 3;
                    } else if (Keyboard.isKeyDown(KeyBindingListener.hotbar5.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 4;
                    } else if (Keyboard.isKeyDown(KeyBindingListener.hotbar6.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 5;
                    } else if (Keyboard.isKeyDown(KeyBindingListener.hotbar7.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 6;
                    } else if (Keyboard.isKeyDown(KeyBindingListener.hotbar8.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 7;
                    } else if (Keyboard.isKeyDown(KeyBindingListener.hotbar9.key)) {
                        minecraft.player.inventory.selectedHotbarSlot = 8;
                    }
                }
            }
        }
    }
}

package com.github.telvarost.clientsideessentials.events;

import blue.endless.jankson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.api.gcapi.api.PreConfigSavedListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import org.lwjgl.input.Mouse;

@EventListener
public class ConfigListener implements PreConfigSavedListener {

    @Override
    public void onPreConfigSaved(int var1, JsonObject jsonObject, JsonObject jsonObject1) {
        /** - Update max stack size on config change */
        Minecraft minecraft = (Minecraft) FabricLoader.getInstance().getGameInstance();
        if (null != minecraft) {
            minecraft.worldRenderer.method_1537();
            minecraft.textRenderer = new TextRenderer(minecraft.options, "/font/default.png", minecraft.textureManager);
            //minecraft.textureManager.reloadTexturesFromTexturePack();
        }
    }
}

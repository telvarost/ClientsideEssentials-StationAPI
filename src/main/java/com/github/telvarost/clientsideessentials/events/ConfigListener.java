package com.github.telvarost.clientsideessentials.events;

import blue.endless.jankson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.api.gcapi.api.PreConfigSavedListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;

@EventListener
public class ConfigListener implements PreConfigSavedListener {

    @Override
    public void onPreConfigSaved(int var1, JsonObject jsonObject, JsonObject jsonObject1) {
        if (  (null != FabricLoader.getInstance())
           && (EnvType.CLIENT == FabricLoader.getInstance().getEnvironmentType())
        ) {
            Minecraft minecraft = (Minecraft) FabricLoader.getInstance().getGameInstance();
            if (null != minecraft) {
                boolean brightnessSettingOld = false;
                boolean brightnessSettingNew = false;

                minecraft.worldRenderer.reload();
                minecraft.textRenderer = new TextRenderer(minecraft.options, "/font/default.png", minecraft.textureManager);

                if (null != jsonObject.getObject("BRIGHTNESS_CONFIG")) {
                    brightnessSettingOld = jsonObject.getObject("BRIGHTNESS_CONFIG").getBoolean("ENABLE_BRIGHTNESS_GUI", false);
                }

                if (null != jsonObject1.getObject("BRIGHTNESS_CONFIG")) {
                    brightnessSettingNew = jsonObject1.getObject("BRIGHTNESS_CONFIG").getBoolean("ENABLE_BRIGHTNESS_GUI", false);
                }

                if (brightnessSettingOld != brightnessSettingNew) {
                    minecraft.textureManager.reload();
                }
            }
        }
    }
}

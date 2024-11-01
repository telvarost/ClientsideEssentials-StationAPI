package com.github.telvarost.clientsideessentials.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.gcapi3.api.PreConfigSavedListener;
import net.glasslauncher.mods.gcapi3.impl.GlassYamlFile;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;

@EventListener
public class ConfigListener implements PreConfigSavedListener {

    @Override
    public void onPreConfigSaved(int source, GlassYamlFile oldValues, GlassYamlFile newValues)
    {
        if (  (null != FabricLoader.getInstance())
           && (EnvType.CLIENT == FabricLoader.getInstance().getEnvironmentType())
        ) {
            Minecraft minecraft = (Minecraft) FabricLoader.getInstance().getGameInstance();
            if (null != minecraft) {
                boolean brightnessSettingOld = false;
                boolean brightnessSettingNew = false;

                minecraft.worldRenderer.reload();
                minecraft.textRenderer = new TextRenderer(minecraft.options, "/font/default.png", minecraft.textureManager);

                if (null != oldValues.getConfigurationSection("BRIGHTNESS_CONFIG")) {
                    brightnessSettingOld = oldValues.getConfigurationSection("BRIGHTNESS_CONFIG").getBoolean("ENABLE_BRIGHTNESS_GUI", false);
                }

                if (null != newValues.getConfigurationSection("BRIGHTNESS_CONFIG")) {
                    brightnessSettingNew = newValues.getConfigurationSection("BRIGHTNESS_CONFIG").getBoolean("ENABLE_BRIGHTNESS_GUI", false);
                }

                if (brightnessSettingOld != brightnessSettingNew) {
                    minecraft.textureManager.reload();
                }
            }
        }
    }
}

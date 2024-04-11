package com.github.telvarost.clientsideessentials;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.*;

public class Config {

    @GConfig(value = "config", visibleName = "ClientsideEssentials")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {

        @ConfigCategory("Graphics Config")
        public GraphicsConfig GRAPHICS_CONFIG = new GraphicsConfig();

        @ConfigCategory("Sound Config")
        public SoundConfig SOUND_CONFIG = new SoundConfig();

        @ConfigName("Use Dismount Keybinding To Exit Vehicles")
        public Boolean SHIFT_EXIT_VEHICLES = true;
    }

    public static class GraphicsConfig {

        @ConfigName("Add Day Counter To F3 Overlay")
        public Boolean ADD_DAY_COUNTER = true;

        @ConfigName("Change Gamma Slider Range")
        public GammaRangeEnum GAMMA_RANGE = GammaRangeEnum.SMALL;

        @ConfigName("Enable Gamma Slider")
        public Boolean ENABLE_GAMMA_SLIDER = true;

        @ConfigName("Enable Gamma Fog")
        public Boolean ENABLE_GAMMA_FOG = false;

        @ConfigName("Enable Gamma GUI")
        public Boolean ENABLE_GAMMA_GUI = false;

        @ConfigName("ENABLE ID Tags")
        public Boolean ENABLE_ID_TAGS = false;

        @ConfigName("Fix Bow Models")
        public Boolean FIX_BOW_MODEL = true;

        @ConfigName("Fix Container Label Rendering")
        public Boolean FIX_CONTAINER_LABEL_RENDERING = true;

        @ConfigName("Fix Death Screen Text")
        public Boolean FIX_DEATH_SCREEN_TEXT = true;

        @ConfigName("Fix Leg Armor On Vehicles")
        public Boolean FIX_LEG_ARMOR_ON_VEHICLES = true;

        @ConfigName("Fix Minecart Flickering")
        public Boolean FIX_MINECART_FLICKERING = true;

        @ConfigName("Fix Screen Scaling")
        public Boolean FIX_SCREEN_SCALING = true;
    }

    public static class SoundConfig {

        @ConfigName("Add Sound: Chest Open")
        public Boolean ADD_CHEST_OPEN_SOUND = true;

        @ConfigName("Add Sound: Chest Close")
        public Boolean ADD_CHEST_CLOSE_SOUND = true;

        @ConfigName("Add Sound: Food Eat")
        public Boolean ADD_FOOD_EAT_SOUND = true;

        @ConfigName("Add Sound: Food Burp")
        public Boolean ADD_FOOD_BURP_SOUND = true;

        @ConfigName("Add Sound: Item Breaking")
        public Boolean ADD_ITEM_BREAK_SOUND = true;

        @ConfigName("Add Sound: Sheep Shear")
        public Boolean ADD_SHEEP_SHEAR_SOUND = true;

        @ConfigName("Add Sound: Minecart Rolling")
        public Boolean ADD_MINECART_ROLLING_SOUND = true;
    }
}

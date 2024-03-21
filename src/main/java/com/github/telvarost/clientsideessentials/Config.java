package com.github.telvarost.clientsideessentials;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.*;

public class Config {

    @GConfig(value = "config", visibleName = "ClientsideEssentials")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {

        @ConfigCategory("Graphics Config")
        public GraphicsConfig GRAPHICS_CONFIG = new GraphicsConfig();

        @ConfigCategory("Score Config")
        public ScoreConfig SCORE_CONFIG = new ScoreConfig();

        @ConfigCategory("Sound Config")
        public SoundConfig SOUND_CONFIG = new SoundConfig();

        @ConfigName("Use \"Shift\" to exit vehicles")
        public Boolean SHIFT_EXIT_VEHICLES = true;
    }

    public static class GraphicsConfig {

        @ConfigName("Add day counter to F3 overlay")
        public Boolean ADD_DAY_COUNTER = true;

        @ConfigName("Disable id tags")
        public Boolean DISABLE_ID_TAGS = true;

        @ConfigName("Fix bow models")
        public Boolean FIX_BOW_MODEL = true;

        @ConfigName("Fix container label rendering")
        public Boolean FIX_CONTAINER_LABEL_RENDERING = true;

        @ConfigName("Fix death screen text")
        public Boolean FIX_DEATH_SCREEN_TEXT = true;

        @ConfigName("Fix leg armor on vehicles")
        public Boolean FIX_LEG_ARMOR_ON_VEHICLES = true;

        @ConfigName("Fix minecart flickering")
        public Boolean FIX_MINECART_FLICKERING = true;

        @ConfigName("Fix screen scaling")
        public Boolean FIX_SCREEN_SCALING = true;
    }

    public static class ScoreConfig {

        @ConfigName("Each block placed adds +1 to score")
        public Boolean ADD_SCORE_ON_BLOCK_PLACED = true;

        @ConfigName("Each block removed adds +1 to score")
        public Boolean ADD_SCORE_ON_BLOCK_REMOVED = true;

        @ConfigName("Each monster mob killed adds +1 to score")
        public Boolean ADD_SCORE_ON_MONSTER_KILLED = true;

        @ConfigName("Each passive mob killed adds +1 to score")
        public Boolean ADD_SCORE_ON_PASSIVE_KILLED = true;
    }

    public static class SoundConfig {

        @ConfigName("Add sound: Chest Open")
        public Boolean ADD_CHEST_OPEN_SOUND = true;

        @ConfigName("Add sound: Chest Close")
        public Boolean ADD_CHEST_CLOSE_SOUND = true;

        @ConfigName("Add sound: Food Eat")
        public Boolean ADD_FOOD_EAT_SOUND = true;

        @ConfigName("Add sound: Food Burp")
        public Boolean ADD_FOOD_BURP_SOUND = true;

        @ConfigName("Add sound: Item Breaking")
        public Boolean ADD_ITEM_BREAK_SOUND = true;

        @ConfigName("Add sound: Sheep Shear")
        public Boolean ADD_SHEEP_SHEAR_SOUND = true;

        @ConfigName("Add sound: Minecart Rolling")
        public Boolean ADD_MINECART_ROLLING_SOUND = true;
    }
}

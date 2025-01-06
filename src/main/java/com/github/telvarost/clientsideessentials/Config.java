package com.github.telvarost.clientsideessentials;

import net.glasslauncher.mods.gcapi3.api.*;

public class Config {

    @ConfigRoot(value = "config", visibleName = "ClientsideEssentials")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {

        @ConfigCategory(name = "Brightness Config")
        public BrightnessConfig BRIGHTNESS_CONFIG = new BrightnessConfig();

        @ConfigCategory(name = "Graphics Config")
        public GraphicsConfig GRAPHICS_CONFIG = new GraphicsConfig();

        @ConfigCategory(name = "Particles Config")
        public ParticlesConfig PARTICLES_CONFIG = new ParticlesConfig();

        @ConfigCategory(name = "Sound Config")
        public SoundConfig SOUND_CONFIG = new SoundConfig();

        @ConfigEntry(name = "Use Dismount Keybinding To Exit Vehicles")
        public Boolean SHIFT_EXIT_VEHICLES = true;
    }

    public static class BrightnessConfig {

        @ConfigEntry(name = "Effective Brightness Slider Range")
        public BrightnessRangeEnum BRIGHTNESS_RANGE = BrightnessRangeEnum.SMALL;

        @ConfigEntry(name = "Enable Brightness Slider")
        public Boolean ENABLE_BRIGHTNESS_SLIDER = true;

        @ConfigEntry(name = "Slider Affects Fog And Clouds")
        public Boolean ENABLE_BRIGHTNESS_FOG = true;

        @ConfigEntry(
                name = "Slider Affects GUI Elements",
                description = "Textures will reload on brightness change"
        )
        public Boolean ENABLE_BRIGHTNESS_GUI = false;
    }

    public static class GraphicsConfig {
        @ConfigEntry(name = "Add Biome To Debug Overlay")
        public Boolean ADD_BIOME_TYPE = true;

        @ConfigEntry(name = "Add Day Counter To Debug Overlay")
        public Boolean ADD_DAY_COUNTER = true;

        @ConfigEntry(name = "Add Hours Played To Debug Overlay")
        public Boolean ADD_TOTAL_PLAY_TIME = true;

        @ConfigEntry(
                name = "Add Light Level To Debug Overlay",
                description = "Calculated at player's eye level"
        )
        public Boolean ADD_LIGHT_LEVEL = true;

        @ConfigEntry(name = "Add Slime Chunk To Debug Overlay")
        public Boolean ADD_SLIME_CHUNK = true;

        @ConfigEntry(name = "Disable ID Tags")
        public Boolean DISABLE_ID_TAGS = true;

        @ConfigEntry(name = "Fix Bow Models")
        public Boolean FIX_BOW_MODEL = true;

        @ConfigEntry(name = "Fix Container Label Rendering")
        public Boolean FIX_CONTAINER_LABEL_RENDERING = true;

        @ConfigEntry(name = "Fix Death Screen Text")
        public Boolean FIX_DEATH_SCREEN_TEXT = true;

        @ConfigEntry(name = "Fix Leg Armor On Vehicles")
        public Boolean FIX_LEG_ARMOR_ON_VEHICLES = true;

        @ConfigEntry(name = "Fix Minecart Flickering")
        public Boolean FIX_MINECART_FLICKERING = true;

        @ConfigEntry(name = "Fix Screen Scaling")
        public Boolean FIX_SCREEN_SCALING = true;
    }

    public static class ParticlesConfig {

        @ConfigEntry(name = "Disable All Particles")
        public Boolean disableAllParticles = false;

        @ConfigEntry(name = "Disable Water Bubble Particle")
        public Boolean disableWaterBubbleParticle = false;

        @ConfigEntry(name = "Disable Smoke Particle")
        public Boolean disableFireSmokeParticle = false;

        @ConfigEntry(name = "Disable Note Particle")
        public Boolean disableNoteParticle = false;

        @ConfigEntry(name = "Disable Portal Particle")
        public Boolean disablePortalParticle = false;

        @ConfigEntry(name = "Disable Explosion Particle")
        public Boolean disableExplosionParticle = false;

        @ConfigEntry(name = "Disable Flame Particle")
        public Boolean disableFlameParticle = false;

        @ConfigEntry(name = "Disable Lava Ember Particle")
        public Boolean disableLavaEmberParticle = false;

        @ConfigEntry(name = "Disable Footstep Particle")
        public Boolean disableFootstepParticle = false;

        @ConfigEntry(name = "Disable Water Splash Particle")
        public Boolean disableWaterSplashParticle = false;

        @ConfigEntry(name = "Disable Large Smoke Particle")
        public Boolean disableLargeFireSmokeParticle = false;

        @ConfigEntry(name = "Disable Redstone Dust Particle")
        public Boolean disableRedDustParticle = false;

        @ConfigEntry(name = "Disable Snowball Particle")
        public Boolean disableSnowballParticle = false;

        @ConfigEntry(name = "Disable Snow Shovel Particle")
        public Boolean disableSnowShovelParticle = false;

        @ConfigEntry(name = "Disable Slime Particle")
        public Boolean disableSlimeParticle = false;

        @ConfigEntry(name = "Disable Heart Particle")
        public Boolean disableHeartParticle = false;
    }

    public static class SoundConfig {

        @ConfigEntry(name = "Add Sound: Chest Open")
        public Boolean ADD_CHEST_OPEN_SOUND = true;

        @ConfigEntry(name = "Add Sound: Chest Close")
        public Boolean ADD_CHEST_CLOSE_SOUND = true;

        @ConfigEntry(name = "Add Sound: Food Eat")
        public Boolean ADD_FOOD_EAT_SOUND = true;

        @ConfigEntry(name = "Add Sound: Food Burp")
        public Boolean ADD_FOOD_BURP_SOUND = true;

        @ConfigEntry(name = "Add Sound: Item Breaking")
        public Boolean ADD_ITEM_BREAK_SOUND = true;

        @ConfigEntry(name = "Add Sound: Sheep Shear")
        public Boolean ADD_SHEEP_SHEAR_SOUND = true;

        @ConfigEntry(name = "Add Sound: Minecart Rolling")
        public Boolean ADD_MINECART_ROLLING_SOUND = true;
    }
}

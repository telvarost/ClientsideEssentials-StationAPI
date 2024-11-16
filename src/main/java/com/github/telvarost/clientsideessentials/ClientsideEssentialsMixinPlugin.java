package com.github.telvarost.clientsideessentials;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class ClientsideEssentialsMixinPlugin implements IMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if (mixinClassName.equals("com.github.telvarost.clientsideessentials.mixin.DeathScreenMixin")) {
            ModHelper.ModHelperFields.IS_MOJANGFIX_LOADED = (  FabricLoader.getInstance().isModLoaded("mojangfixstationapi")
                                                            || FabricLoader.getInstance().isModLoaded("mojangfix")
                                                            );
            return (false == ModHelper.ModHelperFields.IS_MOJANGFIX_LOADED);
        } else if (mixinClassName.equals("com.github.telvarost.clientsideessentials.mixin.MinecraftKeyBindingMixin")) {
            ModHelper.ModHelperFields.IS_MOJANGFIX_LOADED = (  FabricLoader.getInstance().isModLoaded("mojangfixstationapi")
                                                            || FabricLoader.getInstance().isModLoaded("mojangfix")
                                                            );
            return ModHelper.ModHelperFields.IS_MOJANGFIX_LOADED;
        } else if (mixinClassName.equals("com.github.telvarost.clientsideessentials.mixin.MinecraftMixin")) {
            return !FabricLoader.getInstance().isModLoaded("gambac");
        } else if (mixinClassName.equals("com.github.telvarost.clientsideessentials.mixin.FogGameRendererMixin")) {
            return !FabricLoader.getInstance().isModLoaded("ambientoverride");
        } else {
            return true;
        }
    }

    // Boilerplate

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
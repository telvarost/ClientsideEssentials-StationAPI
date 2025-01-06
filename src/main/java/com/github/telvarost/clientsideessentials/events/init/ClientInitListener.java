package com.github.telvarost.clientsideessentials.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.GrassColors;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;

public class ClientInitListener {
    @EventListener
    public void registerColorProviders(ItemColorsRegisterEvent event) {
        event.itemColors.register(
                (item, damage) -> GrassColors.getColor(0.5F, 0.5F),
                Block.GRASS_BLOCK
        );
    }
}

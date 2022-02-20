package com.gmail.val59000mc.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnListener implements Listener {
    @EventHandler
    public static void mobfix(EntitySpawnEvent e){
        Entity type = e.getEntity();
        if (type instanceof Monster) {
            e.setCancelled(true);
        }
    }
}

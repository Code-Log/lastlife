package com.codelog.lastlife.events;

import com.codelog.lastlife.LastLife;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinHandler implements Listener {
    private static boolean teamsFixed = false;

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (!teamsFixed) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team add green");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team add red");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team modify green color green");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team modify red color red");
            teamsFixed = true;
            LastLife.logger.info("Fixed teams!");
        }
    }
}

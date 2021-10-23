package com.codelog.lastlife.events;

import com.codelog.lastlife.LifeContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathHandler implements Listener {

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player p) {
            int remainingLives = LifeContext.get().getPlayerLives(p.getUniqueId()) - 1;
            if (remainingLives == 1) {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                        "team join red " + p.getName());
            }

            if (remainingLives <= 0) {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                        "team leave " + p.getName());
                p.sendMessage(ChatColor.RED + "You have lost all of your lives!");
                LifeContext.get().removePlayer(p.getUniqueId());
                return;
            }

            LifeContext.get().setPlayerLives(p.getUniqueId(), remainingLives);
            ChatColor color = (remainingLives == 1) ? ChatColor.RED : ChatColor.GREEN;
            p.sendMessage(ChatColor.YELLOW + "You have " + color + remainingLives + ChatColor.YELLOW + " lives left!");

            if (event.getEntity().getKiller() == null)
                return;

            if (event.getEntity().getKiller().getUniqueId() == LifeContext.get().getBogeyman()) {
                Player b = Bukkit.getServer().getPlayer(LifeContext.get().getBogeyman());
                if (b == null)
                    return;

                LifeContext.get().setBogeyman(null);

                b.sendMessage(
                        Component.text("You are no longer the bogeyman.")
                        .color(TextColor.color(0x55ff55))
                );
            }
        }
    }

}

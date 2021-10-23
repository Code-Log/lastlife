package com.codelog.lastlife.commands;

import com.codelog.lastlife.LifeContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandLives implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (!LifeContext.get().hasPlayer(p.getUniqueId())) {
                p.sendMessage(Component.text("You are no longer part of the game.").color(TextColor.color(0xff5555)));
                return true;
            }

            int lives = LifeContext.get().getPlayerLives(p.getUniqueId());
            ChatColor color = (lives == 1) ? ChatColor.RED : ChatColor.GREEN;
            p.sendMessage(ChatColor.BLUE + "You have " + color + lives + ChatColor.BLUE + " lives left.");
        } else {
            sender.sendMessage("This is a player-only command!");
        }

        return true;
    }
}

package com.codelog.lastlife.commands;

import com.codelog.lastlife.LifeContext;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSetLives implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Wrong number of arguments!");
            return false;
        }

        Player p;
        if ((p = Bukkit.getServer().getPlayer(args[0])) != null) {
            int lives;
            try {
                lives = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number of lives!");
                return true;
            }

            p.sendMessage(ChatColor.BLUE + "Your lives have been set to " + ChatColor.GREEN + lives);
            LifeContext.get().setPlayerLives(p.getUniqueId(), lives);

            sender.sendMessage(ChatColor.BLUE + "Player " + ChatColor.YELLOW + p.getName() + ChatColor.BLUE
                    + " has had their lives changed to " + lives);
        } else {
            sender.sendMessage(ChatColor.RED + "No such player is online!");
        }

        return true;
    }
}

package com.codelog.lastlife.commands;

import com.codelog.lastlife.LifeContext;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandGiveLife implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player-only command!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Incorrect number of arguments!");
            return false;
        }

        Player p = Bukkit.getServer().getPlayer(args[0]);
        if (p == null) {
            sender.sendMessage(ChatColor.RED + "No such player exists!");
            return true;
        }

        if (!LifeContext.get().hasPlayer(p.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "That player is no longer a part of the game!");
            return true;
        }

        Player senderPlayer = (Player)sender;
        if (LifeContext.get().getPlayerLives(senderPlayer.getUniqueId()) < 2) {
            sender.sendMessage(ChatColor.RED + "You do not have enough lives to give!");
            return true;
        }

        int receiverLives = LifeContext.get().getPlayerLives(p.getUniqueId());
        LifeContext.get().setPlayerLives(p.getUniqueId(), receiverLives + 1);
        int senderLives = LifeContext.get().getPlayerLives(senderPlayer.getUniqueId());
        LifeContext.get().setPlayerLives(senderPlayer.getUniqueId(), senderLives - 1);

        sender.sendMessage(ChatColor.BLUE + "You have successfully donated one life to " + ChatColor.YELLOW + p.getName());
        p.sendMessage(ChatColor.BLUE + "You have received a life from " + ChatColor.YELLOW + senderPlayer.getName());

        return true;
    }
}

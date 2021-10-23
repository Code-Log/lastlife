package com.codelog.lastlife;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args[0].equalsIgnoreCase("init")) {

            if (args.length != 2) {
                sender.sendMessage("Wrong number of arguments!");
                return false;
            }

            String sMaxLives = args[1];
            int maxLives = 6;
            try {
                maxLives = Integer.parseInt(sMaxLives);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid number of lives!");
                return false;
            }


            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team add green");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team add red");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team modify green color green");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team modify red color red");

            for (Player p : LastLife.plugin.getServer().getOnlinePlayers()) {
                Random rand = new Random();
                int lives = rand.nextInt(maxLives - 1) + 2;
                LifeContext.get().setPlayerLives(p.getUniqueId(), lives);

                ChatColor color = ChatColor.GREEN;
                p.sendMessage(ChatColor.BLUE + "You have " + color + lives + ChatColor.BLUE + " lives!");
            }

            return true;
        } else if (args[0].equalsIgnoreCase("bogeyman")) {
            Bukkit.getServer().broadcast(Component.text("A bogeyman has been chosen!")
                    .color(TextColor.color(0xff5555)));

            if (LifeContext.get().getBogeyman() != null) {
                Player bogeyman = Bukkit.getServer().getPlayer(LifeContext.get().getBogeyman());

                bogeyman.sendMessage(ChatColor.RED + "You have failed as the bogeyman and have been reduced to 1 life!");

                LifeContext.get().setPlayerLives(bogeyman.getUniqueId(), 1);
            }

            List<UUID> players = LifeContext.get().getAllPlayers();

            Random rand = new Random();
            Player selectedPlayer = Bukkit.getServer().getPlayer(players.get(rand.nextInt(players.size())));

            LifeContext.get().setBogeyman(selectedPlayer.getUniqueId());
            selectedPlayer.sendMessage(Component.text("You are the bogeyman!").color(TextColor.color(0xff5555)));

            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            File file = new File(LastLife.plugin.getDataFolder(), "context.txt");
            LifeContext.get().readFromFile(file);
            sender.sendMessage(ChatColor.GREEN + "Done!");

            return true;
        } else if (args[0].equalsIgnoreCase("cure")) {
            if (LifeContext.get().getBogeyman() == null) {
                sender.sendMessage(ChatColor.GREEN + "There is no bogeyman");
                return true;
            }

            Player b = Bukkit.getServer().getPlayer(LifeContext.get().getBogeyman());
            assert b != null;
            b.sendMessage(
                    Component.text("You are no longer the bogeyman.")
                            .color(TextColor.color(0x55ff55))
            );
            LifeContext.get().setBogeyman(null);

            return true;
        } else if (args[0].equalsIgnoreCase("forceteams")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team add green");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team add red");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team modify green color green");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "team modify red color red");
            sender.sendMessage(ChatColor.GREEN + "Done!");
            return true;
        }

        return false;
    }
}

package com.codelog.lastlife;

import com.codelog.lastlife.commands.CommandGiveLife;
import com.codelog.lastlife.commands.CommandLives;
import com.codelog.lastlife.commands.CommandSetLives;
import com.codelog.lastlife.events.DeathHandler;
import com.codelog.lastlife.events.PlayerJoinHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class LastLife extends JavaPlugin {
    public static Logger logger;
    public static LastLife plugin;

    @Override
    public void onEnable() {
        logger = getLogger();
        plugin = this;

        getCommand("lastlife").setExecutor(new MainCommand());
        getCommand("lives").setExecutor(new CommandLives());
        getCommand("setlives").setExecutor(new CommandSetLives());
        getCommand("givelife").setExecutor(new CommandGiveLife());
        getServer().getPluginManager().registerEvents(new DeathHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(), this);

        if (!getDataFolder().exists()) {
            logger.info("Data folder doesn't exist! Creating...");
            getDataFolder().mkdirs();
        }

        File file = new File(getDataFolder(), "context.txt");
        LifeContext.get().readFromFile(file);

        logger.info("Enabled LastLife");
    }

    @Override
    public void onDisable() {
        File file = new File(getDataFolder(), "context.txt");
        LifeContext.get().writeToFile(file);
    }
}

package com.codelog.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class LifeContext {
    private Map<UUID, Integer> lifeMap;
    private static LifeContext instance;

    private UUID bogeyman;

    private LifeContext() {
        lifeMap = new HashMap<>();
    }

    public static LifeContext get() {
        instance = (instance == null) ? new LifeContext() : instance;
        return instance;
    }

    public void setPlayerLives(UUID uuid, int lives) {
        lifeMap.put(uuid, lives);
        Player player = Bukkit.getServer().getPlayer(uuid);
        if (player == null)
            return;

        if (lives == 1) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                    "team join red " + player.getName());
        } else if (lives == 0) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                    "team leave " + player.getName());
            removePlayer(uuid);
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                    "team join green " + player.getName());
        }
    }

    public int getPlayerLives(UUID p) {
        if (!lifeMap.containsKey(p))
            lifeMap.put(p, 0);
        return lifeMap.get(p);
    }

    public void removePlayer(UUID p) {
        lifeMap.remove(p);
    }

    public List<UUID> getAllPlayers() {
        return lifeMap.keySet().stream().toList();
    }

    public UUID getBogeyman() { return bogeyman; }
    public void setBogeyman(UUID p) {
        bogeyman = p;
    }

    public boolean hasPlayer(UUID p) { return lifeMap.containsKey(p); }

    public void writeToFile(File file) {
        if (file.exists()) {
            file.delete();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            if (bogeyman != null) {
                writer.write(bogeyman.toString() + "\n");
            } else {

                writer.write("null\n");
            }

            for (UUID p : lifeMap.keySet()) {
                writer.write(p.toString() + "," + lifeMap.get(p) + "\n");
            }

            LastLife.logger.info("Done writing context file!");
            writer.close();
        } catch (IOException e) {
            LastLife.logger.log(Level.SEVERE, "Couldn't save context!");
        }
    }

    public void readFromFile(File file) {
        if (!file.exists()) {
            LastLife.logger.info("No context file, skipping...");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String bogeymanStr = reader.readLine();
            if (bogeymanStr == null)
                return;

            if (!bogeymanStr.equals("null"))
                setBogeyman(UUID.fromString(bogeymanStr));

            String line;
            while ((line = reader.readLine()) != null) {

                int pos = line.indexOf(',');
                String uuid = line.substring(0, pos);
                String livesStr = line.substring(pos + 1);
                int lives = Integer.parseInt(livesStr);

                setPlayerLives(UUID.fromString(uuid), lives);
            }

            LastLife.logger.info("Done reading context file!");
        } catch (IOException e) {
            LastLife.logger.log(Level.SEVERE, "Couldn't read context file!");
        }
    }
}

package com.codelog.lastlife

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException
import java.util.*
import java.util.logging.Level
import kotlin.collections.HashMap

class LifeContext {

    companion object {
        var bogeyman: UUID? = null


        private val lifeMap: MutableMap<UUID, Int>

        init {
            lifeMap = HashMap()
        }

        fun setPlayerLives(uuid: UUID, lives: Int) {
            lifeMap[uuid] = lives
            val server = Bukkit.getServer()
            val player: Player = server.getPlayer(uuid) ?: return

            when (lives) {
                1 -> LastLife.redTeam?.addEntry(player.name)
                0 -> {
                    LastLife.redTeam?.removeEntry(player.name)
                    LastLife.greenTeam?.removeEntry(player.name)
                }
                else -> LastLife.greenTeam?.addEntry(player.name)
            }
        }

        fun getPlayerLives(uuid: UUID): Int {
            if (lifeMap[uuid] == null)
                lifeMap[uuid] = 0

            return lifeMap[uuid] ?: 0
        }

        fun removePlayer(p: UUID) {
            lifeMap.remove(p);
        }

        fun getAllPlayers(): List<UUID> {
            return lifeMap.keys.toList()
        }

        fun hasPlayer(uuid: UUID): Boolean = lifeMap.containsKey(uuid)

        fun writeToFile(file: File) {
            if (file.exists())
                file.delete()

            try {
                val writer = file.bufferedWriter()

                writer.write(bogeyman?.toString() ?: "null");
                writer.newLine()

                for (uuid in lifeMap.keys) {
                    val lives = lifeMap[uuid]
                    writer.write("$uuid,$lives")
                    writer.newLine()
                }

                LastLife.logger.info("Done writing context file!")
                writer.close()
            } catch (e: IOException) {
                LastLife.logger.log(Level.SEVERE, "Couldn't save context!")
            }
        }

        fun readFromFile(file: File) {
            if (!file.exists()) {
                LastLife.logger.warning("No context file, skipping...")
                return
            }

            try {
                val reader = file.bufferedReader()

                val bogeymanStr: String? = reader.readLine()
                if (bogeymanStr == null) {
                    LastLife.logger.log(Level.SEVERE, "Empty context file!")
                    return
                }

                if (bogeymanStr != "null") {
                    bogeyman = UUID.fromString(bogeymanStr)
                }

                for (line in reader.lineSequence()) {
                    val split = line.split(',')
                    setPlayerLives(UUID.fromString(split[0]), split[1].toIntOrNull() ?: 0)
                }

                LastLife.logger.info("Done reading context file!")
            } catch (e: IOException) {
                LastLife.logger.log(Level.SEVERE, "Couldn't read context file!")
            }
        }
    }
}
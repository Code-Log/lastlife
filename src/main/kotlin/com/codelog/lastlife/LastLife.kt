package com.codelog.lastlife

import com.codelog.lastlife.commands.CommandGiveLife
import com.codelog.lastlife.commands.CommandLives
import com.codelog.lastlife.commands.CommandSetLives
import com.codelog.lastlife.commands.MainCommand
import com.codelog.lastlife.events.DeathHandler
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Team
import java.io.File
import java.util.logging.Logger

open class LastLife() : JavaPlugin() {

    companion object {
        var redTeam: Team? = null
        var greenTeam: Team? = null
        var plugin: LastLife? = null
        var logger: Logger = Logger.getAnonymousLogger()
        var dataFolder: File = File("")
    }

    override fun onEnable() {
        plugin = this
        Companion.dataFolder = dataFolder
        Companion.logger = logger
        registerCommands()
        registerTeams()
    }

    override fun onDisable() {
        val file = File(dataFolder, "context.txt")
        LifeContext.writeToFile(file)
    }

    private fun registerCommands() {
        getCommand("lastlife")?.setExecutor(MainCommand())
        getCommand("lives")?.setExecutor(CommandLives())
        getCommand("setlives")?.setExecutor(CommandSetLives())
        getCommand("givelife")?.setExecutor(CommandGiveLife())
        server.pluginManager.registerEvents(DeathHandler(), this)

        if (!dataFolder.exists()) {
            logger.info("Data folder doesn't exist! Creating...")
            dataFolder.mkdirs()
        }

        val file = File(dataFolder, "context.txt")
        LifeContext.readFromFile(file)

        logger.info("Enabled LastLife!")
    }

    private fun registerTeams() {

    }
}


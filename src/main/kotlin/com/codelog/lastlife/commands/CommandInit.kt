package com.codelog.lastlife.commands

import com.codelog.lastlife.Command
import com.codelog.lastlife.CommandDataPackage
import com.codelog.lastlife.LastLife
import com.codelog.lastlife.LifeContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.Team
import kotlin.random.Random

class CommandInit(override val label: String = "init") : Command() {
    override fun execute(dataPackage: CommandDataPackage): Boolean {
        if (dataPackage.args.size != 2) {
            dataPackage.sender.sendMessage(
                Component.text("Wrong number of arguments!", TextColor.color(0xff0000))
            )
            return false
        }

        var red: Team? = null
        var green: Team? = null

        for (team in Bukkit.getServer().scoreboardManager.mainScoreboard.teams) {
            if (team.name == "Red")
                red = team
            if (team.name == "Green")
                green = team
        }

        if (red == null)
            red = Bukkit.getServer().scoreboardManager.mainScoreboard.registerNewTeam("Red")
        if (green == null)
            green = Bukkit.getServer().scoreboardManager.mainScoreboard.registerNewTeam("Green")

        green.color(NamedTextColor.GREEN)
        red.color(NamedTextColor.RED)

        LastLife.redTeam = red
        LastLife.greenTeam = green

        val maxLives = dataPackage.args[1].toIntOrNull() ?: 0
        if (maxLives <= 0) {
            dataPackage.sender.sendMessage(
                Component.text("Invalid number of lives", TextColor.color(0xff0000))
            )
            return true
        }

        val rand = Random(System.nanoTime())
        for (p in Bukkit.getServer().onlinePlayers) {
            val lives = rand.nextInt(maxLives - 1) + 2
            LifeContext.setPlayerLives(p.uniqueId, lives)

            val color = ChatColor.GREEN
            p.sendMessage(ChatColor.BLUE.toString() + "You have $color$lives" + ChatColor.BLUE + " lives!")
        }

        return true
    }
}
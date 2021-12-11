package com.codelog.lastlife.commands

import com.codelog.lastlife.Command
import com.codelog.lastlife.CommandDataPackage
import com.codelog.lastlife.LifeContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import kotlin.random.Random

class CommandBogeyman(override val label: String = "bogeyman") : Command() {
    override fun execute(dataPackage: CommandDataPackage): Boolean {
        Bukkit.getServer().broadcast(
            Component.text("A bogeyman has been chosen!", TextColor.color(0xff5555))
        )

        if (LifeContext.bogeyman != null) {
            val bogeyman: Player? = Bukkit.getServer().getPlayer(LifeContext.bogeyman!!)
            bogeyman?.sendMessage(
                Component.text(
                    "You have failed as the bogeyman and have been reduced to 1 life!",
                    TextColor.color(0xff0000)
                )
            )
            LifeContext.setPlayerLives(bogeyman!!.uniqueId, 1)
        }

        val players = LifeContext.getAllPlayers()
        val selectedPlayer = Bukkit.getServer().getPlayer(players[Random.nextInt(players.size)])
        LifeContext.bogeyman = selectedPlayer?.uniqueId
        selectedPlayer?.sendMessage(Component.text("You are the bogeyman!", TextColor.color(0xff5555)))

        return true
    }
}
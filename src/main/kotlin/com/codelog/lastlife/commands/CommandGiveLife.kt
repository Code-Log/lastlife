package com.codelog.lastlife.commands

import com.codelog.lastlife.Command
import com.codelog.lastlife.CommandDataPackage
import com.codelog.lastlife.LifeContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class CommandGiveLife(override val label: String = "givelife") : Command() {
    override fun execute(dataPackage: CommandDataPackage): Boolean {
        if (dataPackage.sender !is Player) {
            dataPackage.sender.sendMessage(Component.text("This is a player-only command!"))
            return true
        }

        val p: Player? = Bukkit.getServer().getPlayer(dataPackage.args[0])
        if (p == null) {
            dataPackage.sender.sendMessage(
                Component.text("No such player exists!", TextColor.color(0xff0000))
            )
            return true
        }

        if (dataPackage.args.size != 1) {
            dataPackage.sender.sendMessage(
                Component.text("Incorrect number of arguments!", TextColor.color(0xff0000))
            )
            return false
        }

        if (!LifeContext.hasPlayer(p.uniqueId)) {
            dataPackage.sender.sendMessage(
                Component.text("That player is no longer part of the game!", TextColor.color(0xff0000))
            )
            return true
        }

        val senderPlayer: Player = dataPackage.sender
        if (LifeContext.getPlayerLives(senderPlayer.uniqueId) < 2) {
            senderPlayer.sendMessage(
                Component.text("You don't have enough lives to give!", TextColor.color(0xff0000))
            )
            return true
        }

        val receiverLives = LifeContext.getPlayerLives(p.uniqueId)
        LifeContext.setPlayerLives(p.uniqueId, receiverLives + 1)
        val senderLives =  LifeContext.getPlayerLives(senderPlayer.uniqueId)
        LifeContext.setPlayerLives(senderPlayer.uniqueId, senderLives - 1)

        senderPlayer.sendMessage(ChatColor.BLUE.toString() + "You have successfully donated one life to " + ChatColor.YELLOW + p.name)
        p.sendMessage(ChatColor.BLUE.toString() + "You have received a life from " + ChatColor.YELLOW + senderPlayer.name)

        return true
    }
}
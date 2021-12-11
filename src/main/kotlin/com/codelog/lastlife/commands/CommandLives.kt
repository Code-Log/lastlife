package com.codelog.lastlife.commands

import com.codelog.lastlife.Command
import com.codelog.lastlife.CommandDataPackage
import com.codelog.lastlife.LifeContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class CommandLives(override val label: String = "lives") : Command() {
    override fun execute(dataPackage: CommandDataPackage): Boolean {
        if (dataPackage.sender is Player) {
            val p = dataPackage.sender
            if (!LifeContext.hasPlayer(dataPackage.sender.uniqueId)) {
                p.sendMessage(Component.text("You are no longer part of the game.").color(TextColor.color(0xff5555)))
            }

            val lives: Int = LifeContext.getPlayerLives(p.uniqueId)
            val color: ChatColor = when(lives) {
                1 -> ChatColor.RED
                else -> ChatColor.GREEN
            }
            p.sendMessage(ChatColor.BLUE.toString() + "You have " + color + lives + ChatColor.BLUE + " lives left.");
        } else {
            dataPackage.sender.sendMessage("This is a player-only command!")
        }

        return true
    }
}
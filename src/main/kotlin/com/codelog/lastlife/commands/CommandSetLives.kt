package com.codelog.lastlife.commands

import com.codelog.lastlife.Command
import com.codelog.lastlife.CommandDataPackage
import com.codelog.lastlife.LifeContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class CommandSetLives(override val label: String = "setlives") : Command() {
    override fun execute(dataPackage: CommandDataPackage): Boolean {
        if (dataPackage.args.size != 2) {
            dataPackage.sender.sendMessage(
                Component.text("Incorrect number of arguments!", TextColor.color(0xff0000))
            )
            return false
        }

        val p: Player? = Bukkit.getServer().getPlayer(dataPackage.args[0])
        if (p != null) {
            val lives: Int?
            try {
                lives = dataPackage.args[1].toInt()
            } catch (e: NumberFormatException) {
                dataPackage.sender.sendMessage(
                    Component.text("Invalid number of lives", TextColor.color(0xff0000))
                )
                return true
            }

            dataPackage.sender.sendMessage(ChatColor.RED.toString() + "You lives have been set to " + ChatColor.GREEN + lives)
            LifeContext.setPlayerLives(p.uniqueId, lives)
            dataPackage.sender.sendMessage(ChatColor.BLUE.toString() + "Player " + ChatColor.YELLOW + p.name + ChatColor.BLUE
                    + " has had their lives changed to " + lives
            )
        } else {
            dataPackage.sender.sendMessage(
                Component.text("No such player is online!", TextColor.color(0xff0000))
            )
        }

        return true
    }
}
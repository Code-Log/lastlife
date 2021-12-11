package com.codelog.lastlife.commands

import com.codelog.lastlife.Command
import com.codelog.lastlife.CommandDataPackage
import com.codelog.lastlife.LifeContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit

class CommandCure(override val label: String = "cure") : Command() {
    override fun execute(dataPackage: CommandDataPackage): Boolean {
        if (LifeContext.bogeyman == null) {
            dataPackage.sender.sendMessage(Component.text("There is no bogeyman", TextColor.color(0x55ff55)))
            return true
        }

        val bogeyman = Bukkit.getServer().getPlayer(LifeContext.bogeyman!!)
        bogeyman?.sendMessage(Component.text("You are no longer the bogeyman.", TextColor.color(0x55ff55)))

        LifeContext.bogeyman = null
        return true
    }
}
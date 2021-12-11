package com.codelog.lastlife.commands

import com.codelog.lastlife.Command
import com.codelog.lastlife.CommandDataPackage
import com.codelog.lastlife.LastLife
import com.codelog.lastlife.LifeContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import java.io.File

class CommandReload(override val label: String = "reload") : Command() {
    override fun execute(dataPackage: CommandDataPackage): Boolean {
        LifeContext.readFromFile(File(LastLife.dataFolder, "context.txt"))
        dataPackage.sender.sendMessage(Component.text("Done!", TextColor.color(0x55ff55)))
        return true
    }
}
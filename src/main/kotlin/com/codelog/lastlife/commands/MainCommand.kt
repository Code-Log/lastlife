package com.codelog.lastlife.commands

import com.codelog.lastlife.CommandDataPackage
import com.codelog.lastlife.Command

class MainCommand(override val label: String = "lastlife") : Command() {
    override fun execute(dataPackage: CommandDataPackage): Boolean {

        val init = CommandInit()
        val bogeyman = CommandBogeyman()
        val reload = CommandReload()
        val cure = CommandCure()

        return when {
            dataPackage.args[0] == init.label -> init.execute(dataPackage)
            dataPackage.args[0] == bogeyman.label -> bogeyman.execute(dataPackage)
            dataPackage.args[0] == reload.label -> reload.execute(dataPackage)
            dataPackage.args[0] == cure.label -> cure.execute(dataPackage)
            else -> false
        }
    }
}
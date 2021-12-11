package com.codelog.lastlife

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

data class CommandDataPackage(val sender: CommandSender, val command: Command, val label: String, val args: Array<out String>)

abstract class Command : CommandExecutor {
    abstract val label: String

    abstract fun execute(dataPackage: CommandDataPackage) : Boolean

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean = execute(CommandDataPackage(sender, command, label, args))
}
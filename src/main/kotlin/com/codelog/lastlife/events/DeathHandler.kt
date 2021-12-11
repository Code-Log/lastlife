package com.codelog.lastlife.events

import com.codelog.lastlife.LastLife
import com.codelog.lastlife.LifeContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class DeathHandler : Listener {
    @EventHandler
    fun onEntityDeathEvent(event: EntityDeathEvent) {
        if (event.entity is Player) {
            val remainingLives = LifeContext.getPlayerLives(event.entity.uniqueId) - 1
            if (remainingLives == 1)
                LastLife.redTeam?.addEntry(event.entity.name)

            if (remainingLives <= 0) {
                LastLife.redTeam?.removeEntry(event.entity.name)
                LastLife.greenTeam?.removeEntry(event.entity.name)

                event.entity.sendMessage(
                    Component.text("You have lost all of your lives!", TextColor.color(0xff0000))
                )
            }

            LifeContext.setPlayerLives(event.entity.uniqueId, remainingLives)

            val killer = event.entity.killer ?: return

            if (killer.uniqueId == LifeContext.bogeyman) {
                if (LifeContext.bogeyman == null)
                    return

                val bogeyman = Bukkit.getServer().getPlayer(LifeContext.bogeyman!!)
                bogeyman?.sendMessage(
                    Component.text("You are no longer the bogeyman.", TextColor.color(0x55ff55))
                )
            }
        }
    }
}
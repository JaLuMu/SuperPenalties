package de.jalumu.superpenalties.listener

import de.jalumu.superpenalties.handler.PenaltyCacheHandler
import de.jalumu.superpenalties.handler.PenaltyHandler
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.event.PreLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class PenaltyListener : Listener {

    @EventHandler
    fun onChat(event: ChatEvent) {
        if (!event.isCommand && PenaltyHandler.isMuted(event.sender as ProxiedPlayer)) {
            event.message = ""
        }
    }

    @EventHandler
    fun onJoin(event: PostLoginEvent) {
        PenaltyCacheHandler.muteCache.refresh(event.player.uniqueId)
        PenaltyHandler.isBanned(event.player, true)
    }
}

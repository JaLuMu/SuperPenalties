package de.jalumu.superpenalties.listener

import de.jalumu.superpenalties.additions.delete
import de.jalumu.superpenalties.additions.penalties
import de.jalumu.superpenalties.additions.setupMongo
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class PenaltyListener : Listener {

    @EventHandler
    fun onChat(event: ChatEvent) {

    }

    @EventHandler
    fun onJoin(event: PostLoginEvent) {
        event.player.setupMongo()
    }
}

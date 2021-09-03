package de.jalumu.superpenalties

import de.jalumu.superpenalties.commands.AddPenaltyCommand
import de.jalumu.superpenalties.commands.ListPenaltiesCommand
import de.jalumu.superpenalties.commands.PenaltyCommand
import de.jalumu.superpenalties.commands.RemovePenaltyCommand
import de.jalumu.superpenalties.db.SQLDatabase
import de.jalumu.superpenalties.listener.PenaltyListener
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin
import org.bstats.bungeecord.Metrics

class SuperPenalties : Plugin() {

    override fun onEnable() {
        instance = this
        dataFolder.mkdirs()
        SQLDatabase.init()
        ProxyServer.getInstance().pluginManager.registerCommand(this, AddPenaltyCommand())
        ProxyServer.getInstance().pluginManager.registerCommand(this, PenaltyCommand())
        ProxyServer.getInstance().pluginManager.registerCommand(this, RemovePenaltyCommand())
        ProxyServer.getInstance().pluginManager.registerCommand(this, ListPenaltiesCommand())
        ProxyServer.getInstance().pluginManager.registerListener(this, PenaltyListener())

        val metrics = Metrics(this, 12429)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        lateinit var instance: SuperPenalties
    }

}
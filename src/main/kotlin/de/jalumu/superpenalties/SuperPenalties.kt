package de.jalumu.superpenalties

import de.jalumu.superpenalties.commands.AddPenaltyCommand
import de.jalumu.superpenalties.commands.ListPenaltiesCommand
import de.jalumu.superpenalties.commands.PenaltyCommand
import de.jalumu.superpenalties.commands.RemovePenaltyCommand
import de.jalumu.superpenalties.config.SuperPenaltyConfig
import de.jalumu.superpenalties.data.Config
import de.jalumu.superpenalties.db.MongoDatabase
import de.jalumu.superpenalties.listener.PenaltyListener
import de.jalumu.superpenalties.penalty.DynamicPenalty
import de.jalumu.superpenalties.additions.execute
import de.jalumu.superpenalties.additions.register
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin
import org.bstats.bungeecord.Metrics
import java.util.*
import java.util.concurrent.TimeUnit

class SuperPenalties : Plugin() {

    lateinit var mongoDatabase: MongoDatabase

    override fun onEnable() {
        instance = this
        dataFolder.mkdirs()


        ProxyServer.getInstance().pluginManager.registerCommand(this, AddPenaltyCommand())
        ProxyServer.getInstance().pluginManager.registerCommand(this, PenaltyCommand())
        ProxyServer.getInstance().pluginManager.registerCommand(this, RemovePenaltyCommand())
        ProxyServer.getInstance().pluginManager.registerCommand(this, ListPenaltiesCommand())
        ProxyServer.getInstance().pluginManager.registerListener(this, PenaltyListener())

        with(SuperPenaltyConfig.loadConfig("database")!!) {
            mongoDatabase = if (getBoolean("mongo.connectionString.useConnectionString")) {
                MongoDatabase(getString("mongo.connectionString.connectionString"))
            } else {
                MongoDatabase(
                    getString("mongo.server.host"),
                    getInt("mongo.server.port"),
                    getString("mongo.server.database"),
                    getString("mongo.credentials.username"),
                    getString("mongo.credentials.password")
                )
            }
        }

        mongoDatabase.connect()

        Config.init()

        DynamicPenalty(
            null,
            "Hacking",
            "Spieler dof",
            "Console",
            null,
            penaltyTime = 1,
            timeUnit = TimeUnit.DAYS,
            multiplicator = 0
        ).register(UUID.fromString("50757c2c-2519-48e4-96cc-6e830ab655b6")).execute()

        val metrics = Metrics(this, 12429)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        lateinit var instance: SuperPenalties
    }

}
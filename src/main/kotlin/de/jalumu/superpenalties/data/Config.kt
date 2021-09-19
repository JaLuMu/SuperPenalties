package de.jalumu.superpenalties.data

import de.jalumu.superpenalties.SuperPenalties
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File


object Config {

    lateinit var configuration: Configuration

    fun save() {
        ConfigurationProvider.getProvider(YamlConfiguration::class.java)
            .save(configuration, File(SuperPenalties.instance.dataFolder, "config.yml"))
    }

    fun init() {

        val dataFolder: File = SuperPenalties.instance.dataFolder

        if (!dataFolder.exists()) dataFolder.mkdir()

        val file = File(dataFolder.path, "config.yml")

        try {

            if (!file.exists()) {
                file.createNewFile()
            }

            configuration =
                ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(File(dataFolder, "config.yml"))

            if (configuration.contains("message.penaltyExecuted")) {
                MessageData.penaltyExecuted = configuration.getString("message.penaltyExecuted").replace("&", "§")
            } else {
                configuration.set("message.penaltyExecuted", MessageData.penaltyExecuted)
            }
            if (configuration.contains("message.penaltyCreated")) {
                MessageData.penaltyCreated = configuration.getString("message.penaltyCreated").replace("&", "§")
            } else {
                configuration.set("message.penaltyCreated", MessageData.penaltyCreated)
            }
            if (configuration.contains("message.penaltyCouldNotExecuted")) {
                MessageData.penaltyCouldNotExecuted =
                    configuration.getString("message.penaltyCouldNotExecuted").replace("&", "§")
            } else {
                configuration.set("message.penaltyCouldNotExecuted", MessageData.penaltyCouldNotExecuted)
            }
            if (configuration.contains("message.penaltyMute")) {
                MessageData.penaltyMute = configuration.getString("message.penaltyMute").replace("&", "§")
            } else {
                configuration.set("message.penaltyMute", MessageData.penaltyMute)
            }
            if (configuration.contains("message.penaltyBan")) {
                MessageData.penaltyBan = configuration.getString("message.penaltyBan").replace("&", "§")
            } else {
                configuration.set("message.penaltyBan", MessageData.penaltyBan)
            }
            if (configuration.contains("message.penaltyListPlayerNotFound")) {
                MessageData.penaltyListPlayerNotFound =
                    configuration.getString("message.penaltyListPlayerNotFound").replace("&", "§")
            } else {
                configuration.set("message.penaltyListPlayerNotFound", MessageData.penaltyListPlayerNotFound)
            }
            if (configuration.contains("message.addPenaltyUsage")) {
                MessageData.addPenaltyUsage = configuration.getString("message.addPenaltyUsage").replace("&", "§")
            } else {
                configuration.set("message.addPenaltyUsage", MessageData.addPenaltyUsage)
            }
            if (configuration.contains("message.penaltyListUsage")) {
                MessageData.penaltyListUsage = configuration.getString("message.penaltyListUsage").replace("&", "§")
            } else {
                configuration.set("message.penaltyListUsage", MessageData.penaltyListUsage)
            }
            if (configuration.contains("message.penaltyRemoveUsage")) {
                MessageData.penaltyRemoveUsage = configuration.getString("message.penaltyRemoveUsage").replace("&", "§")
            } else {
                configuration.set("message.penaltyRemoveUsage", MessageData.penaltyRemoveUsage)
            }
            if (configuration.contains("message.penaltyUsage")) {
                MessageData.penaltyUsage = configuration.getString("message.penaltyUsage").replace("&", "§")
            } else {
                configuration.set("message.penaltyUsage", MessageData.penaltyUsage)
            }
            if (configuration.contains("message.penaltyList")) {
                MessageData.penaltyList = configuration.getString("message.penaltyList").replace("&", "§")
            } else {
                configuration.set("message.penaltyList", MessageData.penaltyList)
            }

            save()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
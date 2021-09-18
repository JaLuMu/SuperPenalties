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
            if (configuration.contains("message.peneltyCouldNotExecuted")) {
                MessageData.peneltyCouldNotExecuted =
                    configuration.getString("message.peneltyCouldNotExecuted").replace("&", "§")
            } else {
                configuration.set("message.peneltyCouldNotExecuted", MessageData.peneltyCouldNotExecuted)
            }
            if (configuration.contains("message.peneltyMute")) {
                MessageData.peneltyMute = configuration.getString("message.peneltyMute").replace("&", "§")
            } else {
                configuration.set("message.peneltyMute", MessageData.peneltyMute)
            }
            if (configuration.contains("message.peneltyBan")) {
                MessageData.peneltyBan = configuration.getString("message.peneltyBan").replace("&", "§")
            } else {
                configuration.set("message.peneltyBan", MessageData.peneltyBan)
            }
            if (configuration.contains("message.peneltyListPlayerNotFound")) {
                MessageData.peneltyListPlayerNotFound =
                    configuration.getString("message.peneltyListPlayerNotFound").replace("&", "§")
            } else {
                configuration.set("message.peneltyListPlayerNotFound", MessageData.peneltyListPlayerNotFound)
            }
            if (configuration.contains("message.addPenaltyUsage")) {
                MessageData.addPenaltyUsage = configuration.getString("message.addPenaltyUsage").replace("&", "§")
            } else {
                configuration.set("message.addPenaltyUsage", MessageData.addPenaltyUsage)
            }
            if (configuration.contains("message.peneltyListUsage")) {
                MessageData.peneltyListUsage = configuration.getString("message.peneltyListUsage").replace("&", "§")
            } else {
                configuration.set("message.peneltyListUsage", MessageData.peneltyListUsage)
            }
            if (configuration.contains("message.peneltyRemoveUsage")) {
                MessageData.peneltyRemoveUsage = configuration.getString("message.peneltyRemoveUsage").replace("&", "§")
            } else {
                configuration.set("message.peneltyRemoveUsage", MessageData.peneltyRemoveUsage)
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
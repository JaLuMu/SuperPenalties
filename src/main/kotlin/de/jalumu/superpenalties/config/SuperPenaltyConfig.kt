package de.jalumu.superpenalties.config

import de.jalumu.superpenalties.SuperPenalties
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.IOException
import java.nio.file.Files


object SuperPenaltyConfig {

    var provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

    fun loadConfig(name: String): Configuration? {
        val configFile = File(SuperPenalties.instance.dataFolder, "$name.yml")
        if (!configFile.exists()) {
            try {
                val stream = javaClass.classLoader.getResourceAsStream("$name.yml")
                if (stream != null) {
                    Files.copy(stream, configFile.toPath())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return provider.load(configFile)
    }


}
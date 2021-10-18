package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.additions.delete
import de.jalumu.superpenalties.additions.penalties
import de.jalumu.superpenalties.data.MessageData
import de.jalumu.superpenalties.penalty.PenaltyType
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import java.util.concurrent.TimeUnit

class AddPenaltyCommand : Command("addPenalty", "superpenalty.add"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender.hasPermission("superpenalty.add")) {

            try {
                val name = args[0]
                val type = PenaltyType.valueOf(args[1])
                val provider = args[2]
                val handler = args[3]
                val time = args[4]
                val timeUnit = TimeUnit.valueOf(args[5])

                if (type == PenaltyType.DYNAMIC){
                    val multiplicator = args[6]

                }

            } catch (e: Exception) {
                sender.sendMessage(TextComponent(MessageData.addPenaltyUsage))
            }


            (sender as ProxiedPlayer).penalties.first().delete(sender.uniqueId)
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        if (args != null) {
            when (args.size) {
                1 -> return arrayListOf("<penalty_name>", "Hacking", "Griefing", "AFK", "Spam")
                2 -> return arrayListOf("<penalty_type>", "STATIC", "DYNAMIC")
                3 -> return arrayListOf("<penalty_provider>", "SuperPenalties")
                4 -> return arrayListOf("<penalty_handler>", "Ban", "Mute")
                5 -> return arrayListOf(
                    "<penalty_time>",
                    "1",
                    "3",
                    "5",
                    "7",
                    "10",
                    "14",
                    "32",
                    "64",
                    "128",
                    "256",
                    "365"
                )
                6 -> return arrayListOf("<penalty_unit>", "s", "m", "h", "d")
                7 -> if (args[1] == "DYNAMIC") return arrayListOf(
                    "<penalty_multiplicator>",
                    "1",
                    "2",
                    "3",
                    "5",
                    "7",
                    "10"
                )
            }
        }
        return arrayListOf()
    }


}
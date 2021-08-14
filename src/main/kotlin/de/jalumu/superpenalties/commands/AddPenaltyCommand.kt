package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.db.SQLDatabase
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class AddPenaltyCommand : Command("addPenalty", "superpenalty.add"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender.hasPermission("superpenalty.add")) {
            if (args.size == 5) {
                val name = args[0]
                val type = when (args[1]) {
                    "BAN" -> 1
                    "MUTE" -> 2
                    else -> {
                        sender.sendMessage(TextComponent("USAGE: /addPenalty <penalty_name> <penalty_type> <penalty_time> <penalty_unit> <penalty_multiplicator>"))
                        return
                    }
                }
                val time = args[2]
                val unit = args[3]
                val multiplicator = args[4]
                SQLDatabase.execute("INSERT INTO `registered_penalties` (`name`, `type`, `time`, `time_unit`, `multiplicator`) VALUES ('$name', '$type', '$time', '$unit', '$multiplicator');")
                sender.sendMessage(TextComponent("Penalty added"))
            } else {
                sender.sendMessage(TextComponent("USAGE: /addPenalty <penalty_name> <penalty_type> <penalty_time> <penalty_unit> <penalty_multiplicator>"))
            }
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        if (args != null) {
            when (args.size) {
                1 -> return arrayListOf("<penalty_name>", "Hacking", "Griefing", "AFK", "Spam")
                2 -> return arrayListOf("<penalty_type>", "MUTE", "BAN")
                3 -> return arrayListOf(
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
                4 -> return arrayListOf("<penalty_unit>", "s", "m", "h", "d")
                5 -> return arrayListOf("<penalty_multiplicator>", "1", "2", "3", "5")
            }
        }
        return arrayListOf()
    }


}
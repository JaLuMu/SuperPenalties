package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.db.SQLDatabase
import de.jalumu.superpenalties.handler.PenaltyHandler
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class removePenaltyCommand : Command("removePenalty", "superpenalty.remove"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender.hasPermission("superpenalty.remove")) {
            if (args.size == 1) {
                val name = args[0]

        }else {
                sender.sendMessage(TextComponent("USAGE: /removePenalty <penalty_name>"))
            }

        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {

        val list = mutableListOf<String>()
        PenaltyHandler.penalties.forEach {
            list.add(it)
        }
        return list
    }


}
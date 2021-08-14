package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.handler.PenaltyHandler
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class PenaltyCommand : Command("penalty", "superpenalty.penalty"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender.hasPermission("superpenalty.penalty")) {
            if (args.size == 2) {
                val player = ProxyServer.getInstance().getPlayer(args[0])
                val penalty = args[1]
                PenaltyHandler.executePenalty(player, penalty)
                sender.sendMessage(TextComponent("Strafe erfolgreich ausgeteilt!"))
            } else {
                sender.sendMessage(TextComponent("USAGE: /penalty <player_name> <penalty_name>"))
            }
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        if (args != null) {
            when (args.size) {
                1 -> {
                    val list = mutableListOf<String>()
                    ProxyServer.getInstance().players.forEach {
                        list.add(it.name)
                    }
                    return list
                }
                2 -> {
                    val list = mutableListOf<String>()
                    PenaltyHandler.penalties.forEach {
                        list.add(it)
                    }
                    return list
                }
            }
        }
        return arrayListOf()
    }
}
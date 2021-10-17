package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.data.MessageData
import de.jalumu.superpenalties.data.PlayerAdditions.getOfflinePlayerUUID
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
                if (player == null) {
                    val uuid = args[0].getOfflinePlayerUUID()

                    if (uuid == null) {
                        sender.sendMessage(TextComponent(MessageData.penaltyCouldNotExecuted))
                    } else {
                        //PenaltyHandler.executePenalty(uuid, sender.name, penalty)
                        sender.sendMessage(TextComponent(MessageData.penaltyExecuted))
                    }

                } else {
                    //PenaltyHandler.executePenalty(player, sender.name, penalty)
                    sender.sendMessage(TextComponent(MessageData.penaltyExecuted))

                }
            } else {
                sender.sendMessage(TextComponent(MessageData.penaltyUsage))
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
                    //PenaltyHandler.penalties.forEach {
                        //list.add(it)
                   // }
                    return  list
                }
            }
        }
        return arrayListOf()
    }
}

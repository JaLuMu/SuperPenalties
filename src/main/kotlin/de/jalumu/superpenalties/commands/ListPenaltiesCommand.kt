package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.data.MessageData
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class ListPenaltiesCommand : Command("listPenalties", "superpenalty.list"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender.hasPermission("superpenalty.list")) {
            if (args.size == 1) {

            } else {
                sender.sendMessage(TextComponent(MessageData.penaltyListUsage))
            }
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        if (args != null) {
            val list = mutableListOf<String>()
            ProxyServer.getInstance().players.forEach {
                list.add(it.name)
            }
            return list
        }
        return arrayListOf()
    }
}
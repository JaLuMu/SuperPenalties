package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.data.MessageData
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class RemovePenaltyCommand : Command("removePenalty", "superpenalty.remove"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender.hasPermission("superpenalty.remove")) {
            if (args.size == 1) {
                val name = args[0]


                //PenaltyCacheHandler.invalidate()

        }else {
                sender.sendMessage(TextComponent(MessageData.penaltyRemoveUsage))
            }

        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {

        val list = mutableListOf<String>()
        return list
    }


}
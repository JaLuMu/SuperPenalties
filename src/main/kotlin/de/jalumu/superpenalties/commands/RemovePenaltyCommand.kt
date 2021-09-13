package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.data.MessageData
import de.jalumu.superpenalties.db.SQLDatabase
import de.jalumu.superpenalties.db.tables.CurrentPenaltiesTable
import de.jalumu.superpenalties.db.tables.RegisteredPenaltiesTable
import de.jalumu.superpenalties.handler.PenaltyCacheHandler
import de.jalumu.superpenalties.handler.PenaltyHandler
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq

class RemovePenaltyCommand : Command("removePenalty", "superpenalty.remove"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender.hasPermission("superpenalty.remove")) {
            if (args.size == 1) {
                val name = args[0]

                SQLDatabase.database.delete(CurrentPenaltiesTable){
                    it.penalty_name eq name
                }
                SQLDatabase.database.delete(RegisteredPenaltiesTable){
                    it.name eq name
                }

                PenaltyCacheHandler.invalidate()

        }else {
                sender.sendMessage(TextComponent(MessageData.peneltyRemoveUsage))
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
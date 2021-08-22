package de.jalumu.superpenalties.commands

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

                //SQLDatabase.execute("DELETE FROM registered_penalties WHERE name = '$name';")
                //SQLDatabase.execute("DELETE FROM current_penalties WHERE penalty_name = '$name';")

                SQLDatabase.database.delete(CurrentPenaltiesTable){
                    it.penalty_name eq name
                }
                SQLDatabase.database.delete(RegisteredPenaltiesTable){
                    it.name eq name
                }

                PenaltyCacheHandler.invalidate()

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
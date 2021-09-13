package de.jalumu.superpenalties.commands

import de.jalumu.superpenalties.data.MessageData
import de.jalumu.superpenalties.data.PlayerAdditions.getOfflinePlayerUUID
import de.jalumu.superpenalties.db.SQLDatabase
import de.jalumu.superpenalties.db.tables.CurrentPenaltiesTable
import de.jalumu.superpenalties.db.tables.RegisteredPenaltiesTable
import de.jalumu.superpenalties.penalty.Penalty
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import org.ktorm.dsl.*
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ListPenaltiesCommand : Command("listPenalties", "superpenalty.list"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender.hasPermission("superpenalty.list")) {
            if (args.size == 1) {
                val player = ProxyServer.getInstance().getPlayer(args[0])

                var uuid : UUID?

                if (player == null) {
                    uuid = args[0].getOfflinePlayerUUID()
                    if (uuid == null) {
                        sender.sendMessage(TextComponent(MessageData.peneltyListPlayerNotFound))
                        return
                    }
                } else {
                    uuid = player.uniqueId
                }

                var bans = 0
                var mutes = 0
                SQLDatabase.database.from(CurrentPenaltiesTable)
                    .innerJoin(
                        RegisteredPenaltiesTable,
                        on = CurrentPenaltiesTable.penalty_name eq RegisteredPenaltiesTable.name
                    )
                    .select(
                        CurrentPenaltiesTable.uuid,
                        CurrentPenaltiesTable.penalty_name,
                        CurrentPenaltiesTable.penalty_start,
                        RegisteredPenaltiesTable.type,
                        RegisteredPenaltiesTable.time,
                        RegisteredPenaltiesTable.time_unit,
                        RegisteredPenaltiesTable.multiplicator
                    )
                    .where { (CurrentPenaltiesTable.uuid eq uuid!!.toString()) }.forEach { row ->
                        val penalty = row[CurrentPenaltiesTable.penalty_name]!!
                        val type = row[RegisteredPenaltiesTable.type]!!
                        val start = row[CurrentPenaltiesTable.penalty_start]!!
                        val time = row[RegisteredPenaltiesTable.time]!!
                        val unit = row[RegisteredPenaltiesTable.time_unit]!!
                        val multiplicator = row[RegisteredPenaltiesTable.multiplicator]!!

                        val now = LocalDateTime.now()
                        val rawPenaltyTime = when (unit) {
                            "s" -> Duration.ofSeconds(time.toLong())
                            "m" -> Duration.ofMinutes(time.toLong())
                            "h" -> Duration.ofHours(time.toLong())
                            "d" -> Duration.ofDays(time.toLong())
                            else -> Duration.ZERO
                        }

                        var multiplied = 0

                        val typeName = when (type) {
                            1 -> {
                                bans++
                                multiplied = multiplicator*bans
                                "BAN"
                            }
                            2 -> {
                                mutes++
                                multiplied = multiplicator*mutes
                                "MUTE"
                            }
                            else -> "UNKNOWN"
                        }

                        val multipliedPenaltyTime = rawPenaltyTime.multipliedBy(multiplied.toLong())

                        val finalPenaltyTime = start.plus(multipliedPenaltyTime)

                        sender.sendMessage(TextComponent(
                            MessageData.penaltyList
                                .replace("%PENALTYNAME%", penalty)
                                .replace("%typeName%", typeName)
                                .replace("%time%", time.toString())
                                .replace("%timeUnit%", unit)
                                .replace("%multiplicator%", multiplicator.toString())
                                .replace("%multiplied%", multiplied.toString())
                                .replace("%startTime%", start.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                                .replace("%endTime%", finalPenaltyTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                        ))
                    }

            } else {
                sender.sendMessage(TextComponent(MessageData.peneltyListUsage))
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
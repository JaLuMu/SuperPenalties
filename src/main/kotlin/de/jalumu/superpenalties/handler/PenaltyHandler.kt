package de.jalumu.superpenalties.handler

import de.jalumu.superpenalties.db.SQLDatabase
import de.jalumu.superpenalties.db.tables.CurrentPenaltiesTable
import de.jalumu.superpenalties.db.tables.RegisteredPenaltiesTable
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.ktorm.dsl.*
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object PenaltyHandler {

    val penalties: List<String>
        get() {
            val list = mutableListOf<String>()
            val result = SQLDatabase.query("SELECT name from registered_penalties")!!
            while (result.next()) {
                list.add(result.getString("name"))
            }
            return list
        }

    fun executePenalty(player: ProxiedPlayer, penalty: String) {
        //SQLDatabase.execute("INSERT INTO current_penalties (uuid, penalty_name, penalty_start) VALUES ('${player.uniqueId}', '$penalty', DEFAULT);")

        SQLDatabase.database.insert(CurrentPenaltiesTable) {
            set(it.uuid, player.uniqueId.toString())
            set(it.penalty_name, penalty)
            set(it.penalty_start, LocalDateTime.now())
        }

        isMuted(player)
        isBanned(player)
    }

    fun isBanned(player: ProxiedPlayer): Boolean {
        //val result = SQLDatabase.query("SELECT * FROM current_penalties left join registered_penalties ON current_penalties.penalty_name = registered_penalties.name WHERE uuid='${player.uniqueId}' AND type=1;\n")!!
        var history = 0

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
            .where { (CurrentPenaltiesTable.uuid eq player.uniqueId.toString()) and (RegisteredPenaltiesTable.type eq 1) }
            .forEach { row ->
                history++
                val penalty = row[CurrentPenaltiesTable.penalty_name]!!
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

                val multipliedPenaltyTime = rawPenaltyTime.multipliedBy((multiplicator * history).toLong())

                val finalPenaltyTime = start?.plus(multipliedPenaltyTime)

                if (now.isBefore(finalPenaltyTime)) {


                    player.disconnect(
                        TextComponent(
                            ("&6&lKeeeks.de\n" +
                                    "  &e\n" +
                                    "  &c&cDu wurdest gebannt!\n" +
                                    "  &e\n" +
                                    "  &cGrund &8» &7$penalty\n" +
                                    "  &cDauer &8» &7${finalPenaltyTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))}\n" +
                                    "  &e\n" +
                                    "  &7Einen &aEntbannungsantrag &7kannst du auf unseren &eTeamSpeak &7erstellen!\n" +
                                    "  &eTeamSpeak-IP &8» &6Keeeks.de").replace("&", "§")
                        )
                    )
                }

            }
        return false
    }


    fun isMuted(player: ProxiedPlayer): Boolean {
        // val result = SQLDatabase.query("SELECT * FROM current_penalties left join registered_penalties ON current_penalties.penalty_name = registered_penalties.name WHERE uuid='${player.uniqueId}' AND type=2;\n")!!
        var history = 0

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
            .where { (CurrentPenaltiesTable.uuid eq player.uniqueId.toString()) and (RegisteredPenaltiesTable.type eq 2) }
            .forEach { row ->
                history++
                val penalty = row[CurrentPenaltiesTable.penalty_name]!!
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

                val multipliedPenaltyTime = rawPenaltyTime.multipliedBy((multiplicator * history).toLong())

                val finalPenaltyTime = start?.plus(multipliedPenaltyTime)

                if (now.isBefore(finalPenaltyTime)) {
                    player.sendMessage(
                        TextComponent(
                            ("&8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m \n" +
                                    "&7\n" +
                                    "&cDu wurdest gemutet!\n" +
                                    "&7\n" +
                                    "&cGrund &8» &7$penalty\n" +
                                    "&cDauer &8» &7${finalPenaltyTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))}\n" +
                                    "&7\n" +
                                    "&7Einen &aEntbannungsantrag &7kannst du\n" +
                                    "&7auf unseren &eTeamSpeak &7erstellen!\n" +
                                    "&7\n" +
                                    "&eTeamSpeak-IP &8» &6Keeeks.de\n" +
                                    "&7\n" +
                                    "&8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m").replace(
                                "&",
                                "§"
                            )
                        )
                    )
                    return true
                }

            }
        return false
    }

}
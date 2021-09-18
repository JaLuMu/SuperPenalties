package de.jalumu.superpenalties.handler

import de.jalumu.superpenalties.data.MessageData
import de.jalumu.superpenalties.db.SQLDatabase
import de.jalumu.superpenalties.db.tables.CurrentPenaltiesTable
import de.jalumu.superpenalties.db.tables.RegisteredPenaltiesTable
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.PendingConnection
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.ktorm.dsl.forEach
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object PenaltyHandler {

    val penalties: List<String>
        get() {
            val list = mutableListOf<String>()
            SQLDatabase.database.from(RegisteredPenaltiesTable).select(RegisteredPenaltiesTable.name).forEach {
                list.add(it[RegisteredPenaltiesTable.name]!!)
            }
            return list
        }

    fun executePenalty(player: ProxiedPlayer, executor: String, penalty: String) {

        SQLDatabase.database.insert(CurrentPenaltiesTable) {
            set(it.uuid, player.uniqueId.toString())
            set(it.executor, executor)
            set(it.penalty_name, penalty)
            set(it.penalty_start, LocalDateTime.now())
        }

        isMuted(player, true)
        isBanned(player, true)
    }

    fun executePenalty(playerUniqueIdentifier: UUID, executor: String, penalty: String) {

        SQLDatabase.database.insert(CurrentPenaltiesTable) {
            set(it.uuid, playerUniqueIdentifier.toString())
            set(it.executor, executor)
            set(it.penalty_name, penalty)
            set(it.penalty_start, LocalDateTime.now())
        }

        isMuted(playerUniqueIdentifier, true)
        isBanned(playerUniqueIdentifier, null, true)
    }

    fun isBanned(player: ProxiedPlayer, refreshCache: Boolean = false): Boolean {
        var history = 0

        if (refreshCache) {
            PenaltyCacheHandler.banCache.refresh(player.uniqueId)
        }
        PenaltyCacheHandler.banCache.get(player.uniqueId).forEach { penalty ->
            history++

            val multipliedPenaltyTime = penalty.penaltyTime.multipliedBy((penalty.multiplicator * history).toLong())

            val finalPenaltyTime = penalty.startTime.plus(multipliedPenaltyTime)

            if (LocalDateTime.now().isBefore(finalPenaltyTime)) {


                player.disconnect(
                    TextComponent(
                        (MessageData.peneltyBan)
                            .replace("&", "§")
                            .replace("%DURATION%", finalPenaltyTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                            .replace("%REASON%", penalty.name)
                    )
                )
            }

        }
        return false
    }

    fun isBanned(playerUniqueIdentifier: UUID, connection: PendingConnection?, refreshCache: Boolean = false): Boolean {
        var history = 0

        if (refreshCache) {
            PenaltyCacheHandler.banCache.refresh(playerUniqueIdentifier)
        }
        PenaltyCacheHandler.banCache.get(playerUniqueIdentifier).forEach { penalty ->
            history++

            val multipliedPenaltyTime = penalty.penaltyTime.multipliedBy((penalty.multiplicator * history).toLong())

            val finalPenaltyTime = penalty.startTime.plus(multipliedPenaltyTime)

            if (LocalDateTime.now().isBefore(finalPenaltyTime)) {

                connection?.disconnect(
                    TextComponent(
                        (MessageData.peneltyMute)
                            .replace("&", "§")
                            .replace("%DURATION%", finalPenaltyTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                            .replace("%REASON%", penalty.name)
                )
                )
            }

        }
        return false
    }


    fun isMuted(player: ProxiedPlayer, refreshCache: Boolean = false): Boolean {

        var history = 0

        if (refreshCache) {
            PenaltyCacheHandler.muteCache.refresh(player.uniqueId)
        }
        PenaltyCacheHandler.muteCache.get(player.uniqueId).forEach { penalty ->
            history++

            val multipliedPenaltyTime = penalty.penaltyTime.multipliedBy((penalty.multiplicator * history).toLong())

            val finalPenaltyTime = penalty.startTime.plus(multipliedPenaltyTime)

            if (LocalDateTime.now().isBefore(finalPenaltyTime)) {
                player.sendMessage(
                    TextComponent(
                        ("&8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m &8&m \n" +
                                "&7\n" +
                                "&cDu wurdest gemutet!\n" +
                                "&7\n" +
                                "&cGrund &8» &7${penalty.name}\n" +
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

    fun isMuted(playerUniqueIdentifier: UUID, refreshCache: Boolean = false): Boolean {
        var history = 0

        if (refreshCache) {
            PenaltyCacheHandler.muteCache.refresh(playerUniqueIdentifier)
        }
        PenaltyCacheHandler.muteCache.get(playerUniqueIdentifier).forEach { penalty ->
            history++

            val multipliedPenaltyTime = penalty.penaltyTime.multipliedBy((penalty.multiplicator * history).toLong())

            val finalPenaltyTime = penalty.startTime.plus(multipliedPenaltyTime)

            if (LocalDateTime.now().isBefore(finalPenaltyTime)) {
                return true
            }


        }
        return false
    }

}
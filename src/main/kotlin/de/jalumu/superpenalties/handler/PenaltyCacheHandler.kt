package de.jalumu.superpenalties.handler

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import de.jalumu.superpenalties.db.SQLDatabase
import de.jalumu.superpenalties.db.tables.CurrentPenaltiesTable
import de.jalumu.superpenalties.db.tables.RegisteredPenaltiesTable
import de.jalumu.superpenalties.penalty.Penalty
import org.ktorm.dsl.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit


object PenaltyCacheHandler {

    var banCache: LoadingCache<UUID, Array<Penalty>> = CacheBuilder.newBuilder()
        .maximumSize(256)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build(
            object : CacheLoader<UUID, Array<Penalty>>() {
                @Throws(Exception::class)
                override fun load(uuid: UUID): Array<Penalty> {
                    val list = mutableListOf<Penalty>()
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
                        .where { (CurrentPenaltiesTable.uuid eq uuid.toString()) and (RegisteredPenaltiesTable.type eq 1) }
                        .iterator().forEach { row ->
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

                            list.add(Penalty(penalty, start,rawPenaltyTime,multiplicator))
                        }
                    return list.toTypedArray()
                }
            })

    var muteCache: LoadingCache<UUID, Array<Penalty>> = CacheBuilder.newBuilder()
        .maximumSize(256)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build(
            object : CacheLoader<UUID, Array<Penalty>>() {
                @Throws(Exception::class)
                override fun load(uuid: UUID): Array<Penalty> {
                    val list = mutableListOf<Penalty>()
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
                        .where { (CurrentPenaltiesTable.uuid eq uuid.toString()) and (RegisteredPenaltiesTable.type eq 2) }
                        .iterator().forEach { row ->
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

                            list.add(Penalty(penalty, start,rawPenaltyTime,multiplicator))
                        }
                    return list.toTypedArray()
                }
            })

    fun invalidate(){
        banCache.invalidateAll()
        muteCache.invalidateAll()
    }

}
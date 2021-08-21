package de.jalumu.superpenalties.db.tables

import org.ktorm.schema.*

object CurrentPenaltiesTable : Table<Nothing>("current_penalties") {
    val uuid = varchar("uuid").primaryKey()
    val penalty_name = varchar("penalty_name")
    val  penalty_start = datetime("penalty_start")
}
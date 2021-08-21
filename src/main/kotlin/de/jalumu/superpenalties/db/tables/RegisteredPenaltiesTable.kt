package de.jalumu.superpenalties.db.tables

import org.ktorm.schema.*

object RegisteredPenaltiesTable : Table<Nothing>("registered_penalties") {
    val name = varchar("name").primaryKey()
    val type = int("type")
    val time = int("time")
    val time_unit = varchar("time_unit")
    val multiplicator = int("multiplicator")
}
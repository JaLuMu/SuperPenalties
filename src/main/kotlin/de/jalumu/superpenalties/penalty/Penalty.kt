package de.jalumu.superpenalties.penalty

import java.time.LocalDateTime
import java.util.*

open class Penalty(
    val id: Int?,
    val name: String,
    val type: PenaltyType,
    val description: String?,
    val executor: String,
    val executorId: UUID?,
    val executionDay: LocalDateTime
) {


}

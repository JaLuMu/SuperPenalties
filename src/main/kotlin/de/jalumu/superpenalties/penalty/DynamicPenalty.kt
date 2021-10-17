package de.jalumu.superpenalties.penalty

import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

class DynamicPenalty(
    id: Int?,
    name: String,
    description: String?,
    executor: String,
    executorId: UUID?,
    executionDay: LocalDateTime = LocalDateTime.now(),
    val penaltyTime: Int,
    val timeUnit: TimeUnit,
    val multiplicator: Int = 0
) : Penalty(id, name, description, executor, executorId, executionDay) {


}
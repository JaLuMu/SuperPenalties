package de.jalumu.superpenalties.penalty.integrated

import de.jalumu.superpenalties.penalty.Penalty
import java.time.LocalDateTime
import java.util.*

class KickPenalty(
    id: Int,
    description: String?,
    executor: String,
    executorId: UUID?,
    executionDay: LocalDateTime
) : Penalty(id, "Kick", description, executor, executorId, executionDay) {
}
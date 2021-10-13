package de.jalumu.superpenalties.penalty

import de.jalumu.superpenalties.penalty.integrated.KickPenalty
import java.time.LocalDateTime
import java.util.*

fun createKickPenalty(reason: String, executor: String, executorID: UUID?, executionDay: LocalDateTime = LocalDateTime.now()): KickPenalty {
    return KickPenalty(-1, reason, executor,executorID,executionDay)
}

fun Penalty.register(): Penalty {
    return this
}

fun Penalty.execute() {

}
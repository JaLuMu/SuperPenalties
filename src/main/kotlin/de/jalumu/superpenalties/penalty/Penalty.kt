package de.jalumu.superpenalties.penalty

import java.time.Duration
import java.time.LocalDateTime

data class Penalty(val name: String,val startTime: LocalDateTime, val penaltyTime: Duration, val multiplicator: Int)

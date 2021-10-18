package de.jalumu.superpenalties.penalty

abstract class PenaltyTemplate {

    abstract val name: String
    abstract val type: PenaltyType
    abstract val provider: String
    abstract val handler: String

    abstract val meta: Map<String, Any>

    abstract fun createPenalty(): Penalty;


}
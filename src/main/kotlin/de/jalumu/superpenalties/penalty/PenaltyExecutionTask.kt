package de.jalumu.superpenalties.penalty

import net.md_5.bungee.api.connection.ProxiedPlayer

interface PenaltyExecutionTask {

    fun executePenalty(player: ProxiedPlayer, penalty: Penalty)

}
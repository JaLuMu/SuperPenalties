package de.jalumu.superpenalties.penalty

import net.md_5.bungee.api.connection.ProxiedPlayer

enum class DefaultPenalties(val penaltyExecutionTask: PenaltyExecutionTask) {

    NONE(object : PenaltyExecutionTask {
        override fun executePenalty(player: ProxiedPlayer, penalty: Penalty) {

        }
    }),
    KICK(object : PenaltyExecutionTask {
        override fun executePenalty(player: ProxiedPlayer, penalty: Penalty) {
            player.disconnect()
        }
    }),
    BAN(object : PenaltyExecutionTask {
        override fun executePenalty(player: ProxiedPlayer, penalty: Penalty) {
            player.disconnect()
        }
    }),
    MUTE(object : PenaltyExecutionTask {
        override fun executePenalty(player: ProxiedPlayer, penalty: Penalty) {
            player.disconnect()
        }
    }),

}
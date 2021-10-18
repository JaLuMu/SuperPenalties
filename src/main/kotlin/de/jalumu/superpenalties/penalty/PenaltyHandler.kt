package de.jalumu.superpenalties.penalty

import net.md_5.bungee.api.plugin.Listener

abstract class PenaltyHandler : Listener {

    abstract val template: PenaltyTemplate


}
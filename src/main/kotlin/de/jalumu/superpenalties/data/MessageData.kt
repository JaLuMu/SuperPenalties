package de.jalumu.superpenalties.data

object MessageData {
    var penaltyExecuted: String = "Die Penalty wurde ausgeführt!"
    var penaltyCreated: String = "Die Penalty wurde erstellt!"
    var penaltyCouldNotExecuted: String = "Die Penalty konnte nicht angewendet werden!"
    var penaltyListPlayerNotFound: String = "Der Spieler konnte nicht gefunden werden!"
    var penaltyMute: String = "&6&lKeeeks.de\n" +
            "  &e\n" +
            "  &c&cDu wurdest gebannt!\n" +
            "  &e\n" +
            "  &cGrund &8» &7%REASON%\n" +
            "  &cDauer &8» &7%DURATION%\n" +
            "  &e\n" +
            "  &7Einen &aEntbannungsantrag &7kannst du auf unseren &eTeamSpeak &7erstellen!\n" +
            "  &eTeamSpeak-IP &8» &6Keeeks.de"
    var penaltyBan: String = "&6&lKeeeks.de\n" +
            "  &e\n" +
            "  &c&cDu wurdest gebannt!\n" +
            "  &e\n" +
            "  &cGrund &8» &7%REASON%\n" +
            "  &cDauer &8» &7%DURATION%\n" +
            "  &e\n" +
            "  &7Einen &aEntbannungsantrag &7kannst du auf unseren &eTeamSpeak &7erstellen!\n" +
            "  &eTeamSpeak-IP &8» &6Keeeks.de"
    var addPenaltyUsage: String =
        "USAGE: /addPenalty <penalty_name> <penalty_type> <penalty_time> <penalty_unit> <penalty_multiplicator>"
    var penaltyListUsage: String = "USAGE: /listPenalties <player_name>"
    var penaltyRemoveUsage: String = "USAGE: /removePenalty <penalty_name>"
    var penaltyUsage: String = "USAGE: /penalty <player_name> <penalty_name>"
    var penaltyList: String = "%PENALTYNAME%\n" +
            "  Type: %typeName%\n" +
            "  Time: %time%%timeUnit%\n" +
            "  Multiplicator: %multiplicator%\n" +
            "  Multiplied By: %multiplied%\n" +
            "  Start: %startTime%\n" +
            "  End: %endTime%"

}
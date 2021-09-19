![](img/SuperPenalties.png)
![bStats Players](https://img.shields.io/bstats/players/12429) ![bStats Servers](https://img.shields.io/bstats/servers/12429) ![GitHub issues](https://img.shields.io/github/issues/JaLuMu/SuperPenalties) ![GitHub pull requests](https://img.shields.io/github/issues-pr/JaLuMu/Superpenalties) ![GitHub](https://img.shields.io/github/license/JaLuMu/SuperPenalties)

__________

![](img/features.png)

- Add predefined Penalties
- Automatic Penalty hardship calculation
- All messages are customizable
- mariaDB connection

**Work in progess:**
- Add custom Penalties (WIP)
- Redis cache support (WIP)

__________

![](img/commands.png)


- `/addPenalty` Permission: `superpenalty.add` - Register a new Penalty
- `/removePenalty` Permission: `superpenalty.remove` - Removes a Penalty from Database
- `/penalty` Permission: `superpenalty.penalty` - Execute a Penalty
- `/pardon` Permission: `superpenalty.pardon` - Remove a Penalty from a specific Player
- `/listPenalties` Permission: `superpenalty.list` - Shows all executed Penalties from a specific Player

__________

![](img/metrics.png)


This plugin sends statistics to [bStats](https://bstats.org/). This includes the plugin version, operating system, cpu cores, java version, proxy version and current online players.
The collected data is publicly available and can be viewed [here](https://bstats.org/plugin/bungeecord/SuperPenalties/12429).

You can deactivate the statistics in the file `/plugins/bstats/config.yml`.
![](img/bstats.png)

![bstats Metrics](https://bstats.org/signatures/bungeecord/SuperPenalties.svg)
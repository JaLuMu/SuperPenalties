package de.jalumu.superpenalties.additions

import com.google.gson.Gson
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.jalumu.superpenalties.SuperPenalties
import de.jalumu.superpenalties.penalty.DynamicPenalty
import de.jalumu.superpenalties.penalty.Penalty
import de.jalumu.superpenalties.penalty.PenaltyType
import de.jalumu.superpenalties.penalty.StaticPenalty
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.bson.Document
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

val ProxiedPlayer.penalties: MutableList<Penalty>
    get() {
        with(SuperPenalties.instance.mongoDatabase.players) {
            val doc = find(Filters.eq("uuid", this@penalties.uniqueId.toString())).first()!!

            val list = doc.getList("penalties", Document::class.java)

            val penalties = mutableListOf<Penalty>()

            list.forEach {
                val gson = Gson()
                val penalty = gson.fromJson(it.toJson(), Penalty::class.java)
                penalties.add(
                    when (penalty.type) {
                        PenaltyType.STATIC -> gson.fromJson(it.toJson(), StaticPenalty::class.java)
                        PenaltyType.DYNAMIC -> gson.fromJson(it.toJson(), DynamicPenalty::class.java)
                        else -> penalty
                    }
                )

            }

            return penalties
        }

    }

fun ProxiedPlayer.setupMongo() {
    with(SuperPenalties.instance.mongoDatabase.players) {
        val doc = find(Filters.eq("uuid", this@setupMongo.uniqueId.toString())).first()
        if (doc == null) {
            val document = Document("uuid", this@setupMongo.uniqueId.toString())
                .append("name", this@setupMongo.name)
                .append("penalties", listOf<Penalty>())
            insertOne(document)
        } else {
            println("Player is ion db")
            if (!doc.getString("name").equals(this@setupMongo.name)) {
                updateOne(doc, Updates.set("name", this@setupMongo.name))
            }
        }
    }
}


fun String.getOfflinePlayerUUID(): UUID? {
    val name: String = this
    try {
        val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")

        val result: StringBuilder = StringBuilder();
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET";

        val reader: BufferedReader = BufferedReader(
            InputStreamReader(conn.inputStream)
        )
        var cache = reader.readLine()
        while (cache != null) {
            result.append(cache);
            cache = reader.readLine()
        }

        cache = JSONObject(result.toString()).getString("id").toString()

        val splittedUUID = cache.split("")

        cache = ""

        for (i in 1..32) {
            cache += splittedUUID[i]
            if (i == 8 || i == 12 || i == 16 || i == 20) {
                cache += "-"
            }
        }

        return UUID.fromString(cache)
    } catch (e: Exception) {
        return null
    }
}

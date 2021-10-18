package de.jalumu.superpenalties.additions

import com.google.gson.Gson
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.jalumu.superpenalties.SuperPenalties
import de.jalumu.superpenalties.penalty.Penalty
import org.bson.Document
import java.util.*

val Penalty.json: String
get() {return Gson().toJson(this@json)}

fun Penalty.register(uuid: UUID): Penalty {
    with(SuperPenalties.instance.mongoDatabase.players) {
        val doc = find(Filters.eq("uuid", uuid.toString())).first()!!
        updateOne(doc, Updates.push("penalties", Document.parse(this@register.json)))
    }

    return this
}

fun Penalty.delete(uuid: UUID) {
    with(SuperPenalties.instance.mongoDatabase.players) {
        val doc = find(Filters.eq("uuid", uuid.toString())).first()!!
        updateOne(doc,Updates.pull("penalties",Document.parse(this@delete.json)))
    }
}

fun Penalty.execute() {

}
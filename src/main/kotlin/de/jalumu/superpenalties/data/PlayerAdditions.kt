package de.jalumu.superpenalties.data

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object PlayerAdditions {
    fun String.getOfflinePlayerUUID(): UUID? {
        val name : String = this
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
}
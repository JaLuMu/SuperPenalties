package de.jalumu.superpenalties.db

import com.mongodb.*
import com.mongodb.client.MongoCollection
import org.bson.Document

class MongoDatabase {

    lateinit var credential: MongoCredential
    lateinit var serverAddress: ServerAddress
    lateinit var connectionURI: MongoClientURI
    var connectWithString = false

    lateinit var mongoClient: MongoClient
    lateinit var mongoDatabase: MongoDatabase
    lateinit var mongoCollection: MongoCollection<Document>

    constructor(host: String, port: Int, database: String, username: String, password: String) {
        credential = MongoCredential.createCredential(username, database, password.toCharArray())
        serverAddress = ServerAddress(host, port)
    }

    constructor(connectionString: String) {
        this.connectionURI = MongoClientURI(connectionString)
        connectWithString = true
    }


    fun connect() {
        mongoClient = if (connectWithString) {
            MongoClient(connectionURI)
        } else {
            MongoClient(serverAddress, credential, MongoClientOptions.builder().build())
        }
    }

}
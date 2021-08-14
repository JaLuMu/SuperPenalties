package de.jalumu.superpenalties.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import de.jalumu.superpenalties.config.SuperPenaltyConfig
import java.sql.Connection
import java.sql.ResultSet
import java.util.*
import javax.sql.DataSource


object SQLDatabase {

    lateinit var source: DataSource

    fun init() {
        val config = SuperPenaltyConfig.loadConfig("mysql")

        val props = Properties()
        props.setProperty("dataSourceClassName", "org.mariadb.jdbc.MariaDbDataSource")
        props.setProperty("dataSource.serverName", config?.getString("host"))
        props.setProperty("dataSource.portNumber", config?.getInt("port").toString())
        props.setProperty("dataSource.user", config?.getString("user"))
        props.setProperty("dataSource.password", config?.getString("password"))
        props.setProperty("dataSource.databaseName", config?.getString("database"))

        val hikariConfig = HikariConfig(props)

        hikariConfig.maximumPoolSize = config?.getInt("poolsize")!!

        source = HikariDataSource(hikariConfig)

        val conn: Connection = source.connection

        execute(
            "CREATE TABLE IF NOT EXISTS registered_penalties(\n" +
                    "name VARCHAR(25) not null,\n" +
                    "type int not null,\n" +
                    "time int not null,\n" +
                    "time_unit CHAR not null,\n" +
                    "multiplicator int not null);"
        )

        execute(
            "CREATE TABLE IF NOT EXISTS current_penalties(\n" +
                    "\tuuid VARCHAR(36) not null,\n" +
                    "\tpenalty_name VARCHAR(25) not null,\n" +
                    "\tpenalty_start DATETIME default now() not null);"
        )
    }

    fun execute(query: String) {
        val conn: Connection = source.connection
        val statement = conn.prepareStatement(query)
        statement.execute()
        statement.close()
        conn.close()
    }

    fun query(query: String): ResultSet? {
        val conn: Connection = source.connection
        val statement = conn.prepareStatement(query)
        val result = statement.executeQuery()
        statement.close()
        conn.close()
        return result;
    }

}
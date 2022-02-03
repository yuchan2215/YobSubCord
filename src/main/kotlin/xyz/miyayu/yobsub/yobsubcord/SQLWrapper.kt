package xyz.miyayu.yobsub.yobsubcord

import java.sql.Connection
import java.sql.DriverManager

fun getSQLConnection():Connection{
    try {
        Class.forName("org.sqlite.JDBC")
        return DriverManager.getConnection("jdbc:sqlite:data.db")
    } catch (e:Exception){
        throw e
    }
}
fun createTables(){
    try {
        getSQLConnection().use {
            it.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS videos(" +
                    "videoId TEXT PRIMARY KEY," +
                    "channelId TEXT NOT NULL," +
                    "videoTitle TEXT NOT NULL," +
                    "videoStatus INT NOT NULL," +
                    "lastLook TEXT NOT NULL," +
                    "liveStartDate TEXT" +
                    ")")
        }
    }catch(e:Exception){
        throw e
    }
}
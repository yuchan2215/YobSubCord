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
            it.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS videos(videoId TEXT PRIMARY KEY,channelId TEXT,videoTitle TEXT,videoStatus INT,lastLook BLOB)")
        }
    }catch(e:Exception){
        throw e
    }
}
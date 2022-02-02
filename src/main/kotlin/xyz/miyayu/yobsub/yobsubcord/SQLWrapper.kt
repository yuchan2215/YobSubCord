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
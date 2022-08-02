package xyz.miyayu.yobsub.yobsubcord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import xyz.miyayu.yobsub.yobsubcord.discord.JDAWrapper
import xyz.miyayu.yobsub.yobsubcord.discord.PushButton
import xyz.miyayu.yobsub.yobsubcord.discord.commands.Eval
import xyz.miyayu.yobsub.yobsubcord.discord.commands.MakeButton
import xyz.miyayu.yobsub.yobsubcord.discord.commands.Test
import xyz.miyayu.yobsub.yobsubcord.pubsub.subscribe
import java.time.format.DateTimeFormatter

@SpringBootApplication
class YobSubCordApplication

val logger: Logger = LoggerFactory.getLogger(YobSubCordApplication::class.java)
val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//バージョン
val VERSION: String = (YobSubCordApplication::class.java.`package`.implementationVersion ?: "Dev,Dev").split(",")[0]
val GITTAG: String = (YobSubCordApplication::class.java.`package`.implementationVersion ?: "Dev,Dev").split(",")[1]

fun main(args: Array<String>) {
    runApplication<YobSubCordApplication>(*args)
    loadEnv()
    loadDiscord()
    sqlitePatch()
    subscribe()
    //SQLite初期化
    createTables()
    CheckThread().start()

}
private val regex = Regex(pattern = "[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}")
fun sqlitePatch(){
    listOf("lastLook","liveStartDate").forEach{
        getSQLConnection().use{con ->
            con.createStatement().executeUpdate("UPDATE videos SET $it=REPLACE($it,'/','-')")
        }
    }
}
fun loadEnv() {
    EnvWrapper.print()
}

fun loadDiscord() {
    val jda: JDA = JDAWrapper.getJDA()
    jda.presence.activity = Activity.playing("tb.about VERSION:$VERSION TAG:$GITTAG")
    jda.addEventListener(Eval())
    jda.addEventListener(MakeButton())
    jda.addEventListener(PushButton())
    jda.addEventListener(Test())
}

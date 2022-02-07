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

@SpringBootApplication
class YobSubCordApplication

val logger: Logger = LoggerFactory.getLogger(YobSubCordApplication::class.java)

//バージョン
val VERSION: String = (YobSubCordApplication::class.java.`package`.implementationVersion ?: "Dev,Dev").split(",")[0]
val GITTAG: String = (YobSubCordApplication::class.java.`package`.implementationVersion ?: "Dev,Dev").split(",")[1]

fun main(args: Array<String>) {
    runApplication<YobSubCordApplication>(*args)
    loadEnv()
    loadDiscord()
    subscribe()
    //SQLite初期化
    createTables()
    CheckThread().start()
}

fun loadEnv() {
    logger.info("環境変数を読み込みます...")
    logger.info("DISCORDTOKEN:\t%s".format(EnvWrapper.DISCORD_TOKEN))
    logger.info("ADMINROLE:\t%s".format(EnvWrapper.ADMIN_ROLE))
    logger.info("ALERTROLE:\t%s".format(EnvWrapper.ALERT_ROLE))
    logger.info("DMALERTROLE:\t%s".format(EnvWrapper.DM_ALERT_ROLE))
    logger.info("ALERTCHANNEL:\t%s".format(EnvWrapper.ALERT_CHANNEL))
    logger.info("DEBUGUSER:\t%s".format(EnvWrapper.DEBUG_USER))
    logger.info("DEBUGGUILD:\t%s".format(EnvWrapper.DEBUG_GUILD))
    logger.info(
        "DM通知:\t%s".format(
            if (EnvWrapper.IS_DM_ENABLED)
                "有効"
            else
                "無効"
        )
    )
    EnvWrapper.YTCHANNELS.forEach {
        logger.info("YTCHANNELS:\t%s".format(it))
    }
    logger.info("TOKEN:\t%s".format(EnvWrapper.TOKEN))
    logger.info("URL:\t%s".format(EnvWrapper.URL))
}

fun loadDiscord() {
    val jda: JDA = JDAWrapper.getJDA()
    jda.presence.activity = Activity.playing("tb.about VERSION:$VERSION TAG:$GITTAG")
    jda.addEventListener(Eval())
    jda.addEventListener(MakeButton())
    jda.addEventListener(PushButton())
    jda.addEventListener(Test())
}

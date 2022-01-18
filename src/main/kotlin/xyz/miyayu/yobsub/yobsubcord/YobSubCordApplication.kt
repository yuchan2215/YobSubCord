package xyz.miyayu.yobsub.yobsubcord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.security.auth.login.LoginException

@SpringBootApplication
class YobSubCordApplication
val logger = LoggerFactory.getLogger(YobSubCordApplication::class.java)
var jda:JDA? = null
fun main(args: Array<String>) {
    runApplication<YobSubCordApplication>(*args)
    loadEnv()
    loadDiscord()
}

fun loadEnv() {
    logger.info("環境変数を読み込みます...")
    logger.info("DISCORDTOKEN:\t%s".format(EnvWrapper.DISCORD_TOKEN))
}
fun loadDiscord(){
    try {
        jda = JDABuilder.createDefault(EnvWrapper.DISCORD_TOKEN).build()
    }catch(e:LoginException){
        logger.error(e.stackTraceToString())
        logger.error("DiscordBotのログインに失敗しました")
    }catch(e:Exception) {
        logger.error(e.message)
    }
}

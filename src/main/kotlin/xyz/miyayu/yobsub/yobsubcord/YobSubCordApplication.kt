package xyz.miyayu.yobsub.yobsubcord

import net.dv8tion.jda.api.entities.Activity
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YobSubCordApplication
val logger = LoggerFactory.getLogger(YobSubCordApplication::class.java)
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
    JDAWrapper.getJDA().presence.activity = Activity.playing("hoge")
    JDAWrapper.activityStarter()
}

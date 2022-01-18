package xyz.miyayu.yobsub.yobsubcord

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YobSubCordApplication

fun main(args: Array<String>) {
    runApplication<YobSubCordApplication>(*args)
    loadEnv()
}

fun loadEnv() {
    println("環境変数を読み込みます...")
    println("DISCORDTOKEN:\t%s".format(EnvWrapper.DISCORD_TOKEN))
}

package xyz.miyayu.yobsub.yobsubcord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JDAWrapper {
    companion object{
        val VERSION :String = YobSubCordApplication::class.java.`package`.implementationVersion?:"Dev"

        //ロガー
        val LOGGER: Logger = LoggerFactory.getLogger(JDAWrapper::class.java)

        //n秒ごとに内容を切り替える
        private const val CHANGE_TIME = 15000L
        private val activity = Thread{
            //初期化
            val aboutMsg = listOf(
                Pair("Check: tb.about ",10),
                Pair("Made by Miyayu (tw: @yuu1111main)",3),
                Pair("RunningVersion: %s".format(VERSION),3)
            )
            //CHANGE_TIMEミリ秒ごとにメッセージを切り替える
            while(true){
                for (i in aboutMsg.indices) {
                    jda?.presence?.activity = Activity.playing(
                        "%s (%d/%d)".format(
                            aboutMsg[i].first,
                            i+1,
                            aboutMsg.size
                    ))
                    Thread.sleep(aboutMsg[i].second * 1000L)
                }
            }
        }
        private var jda:JDA? = null
        fun getJDA():JDA{
            if(jda != null)return jda!!
            try{
                jda = JDABuilder.createDefault(EnvWrapper.DISCORD_TOKEN).build()
                return jda!!
            }catch(e:Exception){
                error(e)
            }
        }
        fun activityStarter(){
            activity.start()
        }
    }
    init{
        LOGGER
        getJDA()
    }
}
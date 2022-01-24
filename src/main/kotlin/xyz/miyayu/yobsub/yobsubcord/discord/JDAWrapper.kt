package xyz.miyayu.yobsub.yobsubcord.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper

class JDAWrapper {
    companion object{
        //ロガー
        val LOGGER: Logger = LoggerFactory.getLogger(JDAWrapper::class.java)
        private var jda:JDA? = null
        fun getJDA():JDA{
            if(jda != null)return jda!!
            try{
                print("a")
                jda = JDABuilder.createDefault(EnvWrapper.DISCORD_TOKEN).build()
                print("b")
                return jda!!
            }catch(e:Exception){
                e.stackTrace
                print("c")
                error(e)
            }
        }
    }
    init{
        LOGGER
        getJDA()
    }
}
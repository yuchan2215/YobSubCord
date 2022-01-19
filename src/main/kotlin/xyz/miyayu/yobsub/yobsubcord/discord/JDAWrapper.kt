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
                jda = JDABuilder.createDefault(EnvWrapper.DISCORD_TOKEN).build()
                return jda!!
            }catch(e:Exception){
                error(e)
            }
        }
    }
    init{
        LOGGER
        getJDA()
    }
}
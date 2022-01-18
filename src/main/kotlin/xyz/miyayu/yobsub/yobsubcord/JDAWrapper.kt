package xyz.miyayu.yobsub.yobsubcord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.slf4j.LoggerFactory

class JDAWrapper {
    companion object{
        val VERSION :String = YobSubCordApplication::class.java.`package`.implementationVersion?:"Dev"
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
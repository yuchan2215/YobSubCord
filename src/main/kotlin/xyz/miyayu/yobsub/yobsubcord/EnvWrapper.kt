package xyz.miyayu.yobsub.yobsubcord

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

class EnvWrapper {
    companion object {
        private val DOTENV: Dotenv = dotenv {
            ignoreIfMissing = true
        }
        val MENTION_LIVE: Boolean = DOTENV.get("MENTION_LIVE","").equals("true",true)
        val MENTION_MOVIE: Boolean = DOTENV.get("MENTION_MOVIE","").equals("true",true)

        val DISCORD_TOKEN: String = DOTENV.get("DISCORDTOKEN", "")
        val ADMIN_ROLE: String = DOTENV.get("ADMINROLE", "")
        val ALERT_ROLE: String = DOTENV.get("ALERTROLE", "")
        val ALERT_CHANNEL: String = DOTENV.get("ALERTCHANNEL", "")
        val DEBUG_USER: String = DOTENV.get("DEBUGUSER", "")
        val DEBUG_GUILD: String = DOTENV.get("DEBUGGUILD", "")
        val YTCHANNELS: List<String> = DOTENV.get("YTCHANNELS", "").run {
            if (this.isEmpty()) {
                return@run listOf()
            } else {
                return@run this.split(",")
            }
        }
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val TOKEN = (1..10)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        val URL: String = DOTENV.get("URL", "")
        val YT_API: String = DOTENV.get("YTAPI", "")

        fun print(){
            println("MENTION_LIVE:\t$MENTION_LIVE")
            println("MENTION_MOVIE:\t$MENTION_MOVIE")
            println("DISCORD_TOKEN:\t$DISCORD_TOKEN")
            println("ADMIN_ROLE:\t$ADMIN_ROLE")
            println("ALERT_ROLE:\t$ALERT_ROLE")
            println("ALERT_CHANNEL:\t$ALERT_CHANNEL")
            println("DEBUG_USER:\t$DEBUG_USER")
            println("DEBUG_GUILD:\t$DEBUG_GUILD")
            println("YT_CHANNELS:\t${YTCHANNELS.joinToString(",")}")
            println("URL:\t$URL")
            println("YT_API:\t$YT_API")
        }
    }
}

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
    }
}

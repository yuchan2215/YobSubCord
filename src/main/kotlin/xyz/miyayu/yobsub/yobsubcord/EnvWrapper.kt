package xyz.miyayu.yobsub.yobsubcord

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

class EnvWrapper {
    companion object {
        private val DOTENV: Dotenv = dotenv {
            ignoreIfMissing = true
        }
        val DISCORD_TOKEN: String = DOTENV.get("DISCORDTOKEN", "")
        val ADMIN_ROLE: String = DOTENV.get("ADMINROLE","")
        val ALERT_ROLE: String = DOTENV.get("ALERTROLE","")
        val DM_ALERT_ROLE: String = DOTENV.get("DMALERTROLE","")
        val ALERT_CHANNEL: String = DOTENV.get("ALERTCHANNEL","")
        val DEBUG_USER: String = DOTENV.get("DEBUGUSER","")
        val DEBUG_GUILD: String = DOTENV.get("DEBUGGUILD","")
        val IS_DM_ENABLED: Boolean = DM_ALERT_ROLE !=("")
        val YTCHANNELS: List<String> = DOTENV.get("YTCHANNELS","").run{
            if(this.isEmpty()){
                return@run listOf()
            }else{
                return@run this.split(",")
            }
        }
    }
}

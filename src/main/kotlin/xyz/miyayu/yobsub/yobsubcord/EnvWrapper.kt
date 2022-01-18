package xyz.miyayu.yobsub.yobsubcord

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

class EnvWrapper {
    companion object {
        private val DOTENV: Dotenv = dotenv {
            ignoreIfMissing = true
        }
        val DISCORD_TOKEN: String = DOTENV.get("DISCORDTOKEN", "")
    }
}

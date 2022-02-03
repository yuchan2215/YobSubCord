package xyz.miyayu.yobsub.yobsubcord.discord.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import xyz.miyayu.yobsub.yobsubcord.discord.checkPermission

class Test:ListenerAdapter() {
    val logger = LoggerFactory.getLogger(Test::class.java)
    override fun onMessageReceived(event: MessageReceivedEvent) {
        //コマンドかどうか
        if(event.message.contentRaw != "tb.cccrrr")return
        //権限を持っているかどうか
        if(event.member?.let{ checkPermission(it) } == false)return

        event.guild.pruneMemberCache()
        event.guild.getMembersWithRoles(event.guild.getRoleById(EnvWrapper.ALERT_ROLE)).forEach {
            logger.info(it.effectiveName)
        }
    }
}
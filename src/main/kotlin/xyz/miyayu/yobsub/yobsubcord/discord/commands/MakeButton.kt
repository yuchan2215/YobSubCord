package xyz.miyayu.yobsub.yobsubcord.discord.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import xyz.miyayu.yobsub.yobsubcord.discord.checkPermission

class MakeButton :ListenerAdapter(){
    override fun onMessageReceived(event: MessageReceivedEvent) {
        //コマンドかどうか
        if(event.message.contentRaw != "tb.mb")return
        //権限を持っているかどうか
        if(event.member?.let{checkPermission(it)} == false)return

    }
}
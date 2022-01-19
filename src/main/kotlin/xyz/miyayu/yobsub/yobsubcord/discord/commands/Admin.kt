package xyz.miyayu.yobsub.yobsubcord.discord.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Admin :ListenerAdapter(){
    override fun onMessageReceived(event: MessageReceivedEvent) {
        //権限チェック
        val isOwner = event.author.idLong == 192924983872192512L
        if(!isOwner)return;
    }
}
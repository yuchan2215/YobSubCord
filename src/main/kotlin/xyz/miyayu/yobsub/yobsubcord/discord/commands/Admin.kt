package xyz.miyayu.yobsub.yobsubcord.discord.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Admin :ListenerAdapter(){
    override fun onMessageReceived(event: MessageReceivedEvent) {
        //権限チェック
        val isOwner = event.author.idLong == 192924983872192512L
        if(!isOwner)return

        //コマンド削除
        if(event.message.contentRaw == "tb.rmcmd")
            removeCommands(event)
    }
    fun removeCommands(event: MessageReceivedEvent){
        event.jda.retrieveCommands().queue{
            it.forEach{ command ->
                event.message.reply(command.name + "を削除します").queue()
                command.delete().queue()
            }
            event.message.reply("処理が完了しました")
        }
    }
}
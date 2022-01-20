package xyz.miyayu.yobsub.yobsubcord.discord.commands

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege

class Enable :ListenerAdapter(){
    override fun onMessageReceived(event: MessageReceivedEvent) {
        //管理者権限をもっていないなら
        if(event.member?.hasPermission(Permission.ADMINISTRATOR) == false) return

        //tb.getではないなら
        if(event.message.contentRaw != "tb.get")return

        try {
            val commandid = event.jda.retrieveCommands().complete()[0].idLong
            val privilege = CommandPrivilege(CommandPrivilege.Type.USER, true, event.author.idLong)
            event.guild.updateCommandPrivilegesById(commandid, privilege).queue()
            event.message.reply("権限を付与しました").queue()
        }catch(e: Exception){
            event.message.reply("権限の付与に失敗しました").queue()
        }
    }
}
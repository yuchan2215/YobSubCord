package xyz.miyayu.yobsub.yobsubcord.discord.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData

class Admin :ListenerAdapter(){
    override fun onMessageReceived(event: MessageReceivedEvent) {
        //権限チェック
        val isOwner = event.author.idLong == 192924983872192512L
        if(!isOwner)return

        //コマンド削除
        if(event.message.contentRaw == "tb.rmcmd")
            removeCommands(event)
        else if(event.message.contentRaw == "tb.rgcmd")
            registerCommands(event)
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

    fun registerCommands(event: MessageReceivedEvent){
        val command = CommandData("makebutton","ロールを付与・剥奪するボタンを作成します")
            .addOption(OptionType.ROLE,"workrole","付与・剥奪するロール",true)
            .addOption(OptionType.STRING,"message","ボタンの説明文",true)
            .addOption(OptionType.BOOLEAN,"canremove","剥奪ボタンを作成しますか？(デフォルト：はい)",false)
            .addOption(OptionType.STRING,"gettext","付与ボタンのテキスト",false)
            .addOption(OptionType.STRING,"removetext","剥奪ボタンのテキスト",false)
            .setDefaultEnabled(false)
        event.jda.upsertCommand(command).queue()
        event.message.reply("コマンドの追加処理を行いました。").queue()
    }
}
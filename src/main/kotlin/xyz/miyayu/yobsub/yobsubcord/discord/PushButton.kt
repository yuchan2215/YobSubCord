package xyz.miyayu.yobsub.yobsubcord.discord

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper

class PushButton:ListenerAdapter() {
    override fun onButtonClick(event: ButtonClickEvent) {
        //Live-Alert
        if(event.button?.id.let{it == "on_mention"}){
            liveAlert((event))
        }
        //Live-Alert(DM)
        else if(event.button?.id.let{it == "on_dm"}){
            liveAlertDm(event)
        }
        //解除
        else if(event.button?.id.let{it == "clear"}){
            removeRoles(event)
        }
    }
    fun liveAlert(event: ButtonClickEvent){
        val role = event.jda.getRoleById(EnvWrapper.ALERT_ROLE)
        try{
            if(event.member!!.roles.contains(role!!)){
                event.reply("すでに購読しています！").setEphemeral(true).queue()
            }else {
                event.guild!!.addRoleToMember(event.member!!, role).queue()
                event.reply("処理が正常に完了しました！購読ありがとうございます。").setEphemeral(true).queue()
            }
        }catch(e:Exception){
            event.reply("エラーが発生したためロールを付与することができませんでした。運営に報告してください。\tエラー内容：" + e.message).queue()
        }
    }
    fun liveAlertDm(event: ButtonClickEvent){

    }
    fun removeRoles(event: ButtonClickEvent){

    }
}
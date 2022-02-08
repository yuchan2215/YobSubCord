package xyz.miyayu.yobsub.yobsubcord.discord

import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import xyz.miyayu.yobsub.yobsubcord.pubsub.BEFORE_LIVE
import xyz.miyayu.yobsub.yobsubcord.pubsub.Notification
import xyz.miyayu.yobsub.yobsubcord.pubsub.ONE_MIN_ERROR

class PushButton : ListenerAdapter() {
    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        //Live-Alert
        if (event.button.id.let { it == "on_mention" }) {
            liveAlert((event))
        }
        //Live-Alert(DM)
        /**else if (event.button.id.let { it == "on_dm" }) {
            liveAlertDm(event)
        }**/
        //解除
        else if (event.button.id.let { it == "clear" }) {
            removeRoles(event)
        }else if(event.button.id?.startsWith("R") == true){
            val result = Notification().onPost(event.button.id!!.substring(1))
            if(result.body == BEFORE_LIVE){
                event.reply("現在ライブ待ちではないようです！\n教えてくれてありがとうございました！\nボタンを削除します...").setEphemeral(true).queue()
                event.editButton(null).queue()
            }else if(result.body == ONE_MIN_ERROR){
                event.reply("このボタンは全てのユーザーを合わせて１分間隔でしか使用することができません。使えるまで少々お待ちください").setEphemeral(true).queue()
            }else {
                event.reply("状況を教えてくれてありがとうございました！更新しました。\nボタンを削除します...").setEphemeral(true).queue()
                event.editButton(null).queue()
            }
        }
    }

    private fun liveAlert(event: ButtonInteractionEvent) {
        val role = event.jda.getRoleById(EnvWrapper.ALERT_ROLE)
        giveRole(event, role)
    }

    /**private fun liveAlertDm(event: ButtonInteractionEvent) {
        if (!EnvWrapper.IS_DM_ENABLED) {
            event.reply("現在DMでの通知は行っていません。").setEphemeral(true).queue()
            return
        }
        val role = event.jda.getRoleById(EnvWrapper.DM_ALERT_ROLE)
        giveRole(event, role)
    }**/

    private fun giveRole(event: ButtonInteractionEvent, role: Role?) {
        try {
            if (event.member!!.roles.contains(role!!)) {
                event.reply("すでに購読しています！").setEphemeral(true).queue()
            } else {
                event.guild!!.addRoleToMember(event.member!!, role).queue()
                event.reply("処理が正常に完了しました！購読ありがとうございます。").setEphemeral(true).queue()
            }
        } catch (e: Exception) {
            event.reply("エラーが発生したためロールを付与することができませんでした。運営に報告してください。\tエラー内容：" + e.message).queue()
            e.stackTrace
        }
    }

    private fun removeRoles(event: ButtonInteractionEvent) {
        val code = removeRole(event, event.jda.getRoleById(EnvWrapper.ALERT_ROLE))/** ||
                if (EnvWrapper.IS_DM_ENABLED)
                    removeRole(event, event.jda.getRoleById(EnvWrapper.DM_ALERT_ROLE))
                else false**/

        if (!code) event.reply("正常に処理が完了しました").setEphemeral(true).queue()

    }

    private fun removeRole(event: ButtonInteractionEvent, role: Role?): Boolean {
        try {
            if (!event.member!!.roles.contains(role))
                return false
            event.guild!!.removeRoleFromMember(event.member!!, role!!).queue()
            return false
        } catch (e: Exception) {
            event.reply("処理に失敗したため購読解除ができませんでした。運営に報告してください。\tエラー内容：" + e.message).queue()
            e.stackTrace
            return true
        }
    }
}
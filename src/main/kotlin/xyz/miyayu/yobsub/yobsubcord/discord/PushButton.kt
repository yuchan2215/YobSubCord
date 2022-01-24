package xyz.miyayu.yobsub.yobsubcord.discord

import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper

class PushButton : ListenerAdapter() {
    override fun onButtonClick(event: ButtonClickEvent) {
        //Live-Alert
        if (event.button?.id.let { it == "on_mention" }) {
            liveAlert((event))
        }
        //Live-Alert(DM)
        else if (event.button?.id.let { it == "on_dm" }) {
            liveAlertDm(event)
        }
        //解除
        else if (event.button?.id.let { it == "clear" }) {
            removeRoles(event)
        }
    }

    fun liveAlert(event: ButtonClickEvent) {
        val role = event.jda.getRoleById(EnvWrapper.ALERT_ROLE)
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

    fun liveAlertDm(event: ButtonClickEvent) {
        if (!EnvWrapper.IS_DM_ENABLED) {
            event.reply("現在DMでの通知は行っていません。").setEphemeral(true).queue()
            return
        }
        val role = event.jda.getRoleById(EnvWrapper.DM_ALERT_ROLE)
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

    fun removeRoles(event: ButtonClickEvent) {
        val code = removeRole(event, event.jda.getRoleById(EnvWrapper.ALERT_ROLE))
                || removeRole(event, event.jda.getRoleById(EnvWrapper.DM_ALERT_ROLE))

        if (!code) event.reply("正常に処理が完了しました").setEphemeral(true).queue()

    }

    fun removeRole(event: ButtonClickEvent, role: Role?): Boolean {
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
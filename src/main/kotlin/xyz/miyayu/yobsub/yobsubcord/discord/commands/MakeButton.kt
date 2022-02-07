package xyz.miyayu.yobsub.yobsubcord.discord.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import xyz.miyayu.yobsub.yobsubcord.discord.checkPermission

class MakeButton : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        //コマンドかどうか
        if (event.message.contentRaw != "tb.mb") return
        //権限を持っているかどうか
        if (event.member?.let { checkPermission(it) } == false) return

        //ボタンの定義
        val component = mutableListOf(
            Button.primary("on_mention", "\uD83D\uDCACメンション通知ON"),
            Button.success("on_dm", "\uD83D\uDCEBDM通知ON"),
            Button.danger("clear", "通知解除")
        )

        //DMが無効ならボタンを削除
        if (!EnvWrapper.IS_DM_ENABLED)
            component.removeAt(1)

        //メッセージを送信
        event.channel.sendMessage("Live通知を受け取るかどうか、このボタンで設定できます！")
            .setActionRows(ActionRow.of(component)).queue()

    }
}
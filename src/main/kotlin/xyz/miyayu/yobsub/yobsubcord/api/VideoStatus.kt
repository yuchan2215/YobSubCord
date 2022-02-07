package xyz.miyayu.yobsub.yobsubcord.api

enum class VideoStatus(val dataValue: Int, val notificationText: String,val dmText:String) {
    VIDEO(0, "［:tv:投稿通知］<@&%s>\n**%s**\n%s","［:tv:投稿通知］%s"),
    PRE_LIVE(1, "［:calendar:ライブ予定通知］\n［予定時刻］%s(日本時間)\n**%s**\n%s","［:calendar:ライブ予定通知］\n［予定時刻］%s(日本時間)\n%s"),
    NOW_LIVE(2, "［:tv:ライブ通知］<@&%s>\n**%s**\n%s","［:tv:ライブ通知］\n%s"),
    DELETED(3, "","");

    companion object {
        fun fromInt(value: Int) = values().first { it.dataValue == value }
    }
}
package xyz.miyayu.yobsub.yobsubcord.discord


import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import xyz.miyayu.yobsub.yobsubcord.api.Video
import xyz.miyayu.yobsub.yobsubcord.api.VideoStatus
import xyz.miyayu.yobsub.yobsubcord.formatter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


fun alert(video: Video) {
    val message: String =
        if (video.videoStatus == VideoStatus.PRE_LIVE) {
            video.videoStatus.notificationText.format(
                toJapanTimeString(video.scheduledTime!!),
                video.videoTitle,
                getURL(video.videoId)
            )
        } else {
            video.videoStatus.notificationText.format(
                EnvWrapper.ALERT_ROLE,
                video.videoTitle,
                getURL(video.videoId)
            )
        }
    val channelType = JDAWrapper.getJDA().getGuildChannelById(EnvWrapper.ALERT_CHANNEL)?.type
    val textChannel = JDAWrapper.getJDA().getTextChannelById(EnvWrapper.ALERT_CHANNEL)

    val messageBuilder = MessageBuilder()
    messageBuilder.setContent(message)

    if (video.videoStatus == VideoStatus.PRE_LIVE) {
        messageBuilder.setActionRows(ActionRow.of(Button.primary("R${video.videoId}", "\uD83D\uDD03 再読み込み")))
    }

    if (channelType == ChannelType.NEWS) {
        JDAWrapper.getJDA().getNewsChannelById(EnvWrapper.ALERT_CHANNEL)?.sendMessage(messageBuilder.build())?.queue()
    } else {
        textChannel?.sendMessage(messageBuilder.build())?.queue()
    }
}

fun toJapanTimeString(localDateTime: LocalDateTime): String {
    val utcOffsetDateTime = localDateTime.atZone(ZoneOffset.UTC)
    val jstOffsetDateTime = utcOffsetDateTime.withZoneSameInstant(ZoneId.of("Asia/Tokyo"))
    val jstLocalDateTime = jstOffsetDateTime.toLocalDateTime()
    return jstLocalDateTime.format(formatter)
}

fun getURL(videoId: String): String {
    return ("https://www.youtube.com/watch?v=$videoId")
}
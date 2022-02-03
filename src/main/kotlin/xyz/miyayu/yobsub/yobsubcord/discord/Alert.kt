package xyz.miyayu.yobsub.yobsubcord.discord


import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import xyz.miyayu.yobsub.yobsubcord.api.Video
import xyz.miyayu.yobsub.yobsubcord.api.VideoStatus
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


fun alert(video: Video){
    var message:String =
        if(video.videoStatus == VideoStatus.PRE_LIVE){
            video.videoStatus.notificationText.format(
                toJapanTimeString(video.scheduledTime!!),
                video.videoTitle,
                getURL(video.videoId)
            )
        }else{
            video.videoStatus.notificationText.format(
                EnvWrapper.ALERT_ROLE,
                video.videoTitle,
                getURL(video.videoId)
            )
        }
    val textChannel = JDAWrapper.getJDA().getTextChannelById(EnvWrapper.ALERT_CHANNEL)
    textChannel?.sendMessage(message)?.queue()
}
fun toJapanTimeString(localDateTime: LocalDateTime):String{
    val utcOffsetDateTime = localDateTime.atZone(ZoneOffset.UTC)
    val jstOffsetDateTime = utcOffsetDateTime.withZoneSameInstant(ZoneId.of("Asia/Tokyo"))
    val jstLocalDateTime = jstOffsetDateTime.toLocalDateTime()
    return jstLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
}
fun getURL(videoId:String):String{
    return("https://www.youtube.com/watch?v=$videoId")
}
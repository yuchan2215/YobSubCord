package xyz.miyayu.yobsub.yobsubcord.pubsub

import xyz.miyayu.yobsub.yobsubcord.api.VideoStatus
import xyz.miyayu.yobsub.yobsubcord.formatter
import xyz.miyayu.yobsub.yobsubcord.getSQLConnection
import java.time.LocalDateTime


data class SQLVideo(
    val videoId: String,
    val channelId: String,
    val videoTitle: String,
    val videoStatus: VideoStatus,
    val lastLook: LocalDateTime,
    val liveStartDate: LocalDateTime?
)

fun getSQLVideo(videoId: String): SQLVideo {
    try {
        getSQLConnection().use {
            val pstmt =
                it.prepareStatement("SELECT * FROM videos WHERE videoId = ?")
            pstmt.setString(1, videoId)
            val result = pstmt.executeQuery()
            result.next()
            return SQLVideo(
                result.getString("videoId"),
                result.getString("channelId"),
                result.getString("videoTitle"),
                VideoStatus.fromInt(result.getInt("videoStatus")),
                toLocalDatetime(result.getString("lastLook")),
                result.getString("liveStartDate").run {
                    if (this == null) return@run null
                    return@run toLocalDatetime(this)
                }
            )
        }
    } catch (e: Exception) {
        throw e
    }
}

private fun toLocalDatetime(stringData: String): LocalDateTime {
    val dtft = formatter
    return LocalDateTime.parse(stringData, dtft)
}

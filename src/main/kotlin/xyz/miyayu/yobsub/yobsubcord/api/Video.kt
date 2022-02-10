package xyz.miyayu.yobsub.yobsubcord.api

import org.json.JSONObject
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Video(
    val videoId: String,
    val videoTitle: String,
    val videoStatus: VideoStatus,
    val datetime: LocalDateTime,
    val scheduledTime: LocalDateTime?,
    val channelId: String
)

fun getVideo(videoId: String): Video {
    try {
        //URl作成
        val url =
            URL("https://www.googleapis.com/youtube/v3/videos?key=${EnvWrapper.YT_API}&id=${videoId}&part=snippet,liveStreamingDetails")
        val http = url.openConnection() as HttpURLConnection
        http.requestMethod = "GET"
        http.connect()

        //結果の処理
        val reader = BufferedReader(InputStreamReader(http.inputStream))
        val result = reader.readText()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

        //JSONの処理
        val jsonObj = JSONObject(result)
        val items = jsonObj.getJSONArray("items")
        val snippet = items.getJSONObject(0).getJSONObject("snippet")
        val title = snippet.getString("title")
        var scheduledTime: LocalDateTime? = null
        val channelId = snippet.getString("channelId")
        val dateTime = snippet.getString("liveBroadcastContent").run {
            if (this?.equals("live") == true) {
                val liveStreamingDetails = items.getJSONObject(0).getJSONObject("liveStreamingDetails")
                return@run liveStreamingDetails.getString("actualStartTime")
            } else if (this?.equals("upcoming") == true) {
                val liveStreamingDetails = items.getJSONObject(0).getJSONObject("liveStreamingDetails")
                scheduledTime = LocalDateTime.parse(liveStreamingDetails.getString("scheduledStartTime"), formatter)
            }
            return@run snippet.getString("publishedAt")
        }

        //日付の処理
        val localDateTime = LocalDateTime.parse(dateTime, formatter)

        //ライブかどうかそうでないかを判定する
        val videoStatus = snippet.getString("liveBroadcastContent").run {
            if (this?.equals("live") == true) {
                return@run VideoStatus.NOW_LIVE
            } else if (this?.equals("upcoming") == true) {
                return@run VideoStatus.PRE_LIVE
            } else {
                return@run VideoStatus.VIDEO
            }
        }
        return Video(videoId, title, videoStatus, localDateTime, scheduledTime, channelId)

    } catch (ex: Exception) {
        throw ex
    }
}
fun getVideos(channelId: String):List<String>{
    try {
        val listId = channelId.replaceFirst("UC", "UU")
        //URl作成
        val url =
            URL("https://www.googleapis.com/youtube/v3/playlistItems?key=${EnvWrapper.YT_API}&playlistId=${listId}&part=contentDetails&maxResults=1")
        val http = url.openConnection() as HttpURLConnection
        http.requestMethod = "GET"
        http.connect()

        //結果の処理
        val reader = BufferedReader(InputStreamReader(http.inputStream))
        val result = reader.readText()
        val jsonObj = JSONObject(result)
        val tempList = mutableListOf<String>()
        jsonObj.getJSONArray("items").toList().forEach {
            val item = it as HashMap<*, *>
            tempList.add((item["contentDetails"] as HashMap<*,*>)["videoId"] as String)
        }
        return tempList
    }catch(e:Exception){
        throw e
    }
}
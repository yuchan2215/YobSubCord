package xyz.miyayu.yobsub.yobsubcord.api

import org.json.JSONObject
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class video(val videoId:String,val videoTitle:String,val videoStatus:VideoStatus,val datetime: LocalDateTime)

fun getVideo(videoId: String):video{
    try{
        //URl作成
        val url = URL("https://www.googleapis.com/youtube/v3/videos?key=${EnvWrapper.YT_API}&id=${videoId}&part=snippet")
        val http = url.openConnection() as HttpURLConnection
        http.requestMethod = "GET"
        http.connect()

        //結果の処理
        val reader = BufferedReader(InputStreamReader(http.inputStream))
        val result = reader.readText()

        //JSONの処理
        val jsonObj = JSONObject(result)
        val items = jsonObj.getJSONArray("items")
        val snippet = items.getJSONObject(0).getJSONObject("snippet")
        val title = snippet.getString("title")
        val dateTime = snippet.getString("publishedAt")

        //日付の処理
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val localDateTime = LocalDateTime.parse(dateTime,formatter)

        //ライブかどうかそうでないかそ判定する
        val videoStatus = snippet.getString("liveBroadcastContent").run{
            if(this?.equals("live") == true){
                return@run VideoStatus.NOW_LIVE
            }else if(this?.equals("upcoming") == true){
                return@run VideoStatus.PRE_LIVE
            }else{
                return@run VideoStatus.VIDEO
            }
        }
        return video(videoId,title,videoStatus,localDateTime)

    }catch(ex:Exception){
        throw ex
    }
}
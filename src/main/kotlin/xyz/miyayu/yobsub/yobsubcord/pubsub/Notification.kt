package xyz.miyayu.yobsub.yobsubcord.pubsub

import org.codehaus.groovy.syntax.Types
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import xyz.miyayu.yobsub.yobsubcord.api.VideoStatus
import xyz.miyayu.yobsub.yobsubcord.api.getVideo
import xyz.miyayu.yobsub.yobsubcord.discord.alert
import xyz.miyayu.yobsub.yobsubcord.getChildNodeMaps
import xyz.miyayu.yobsub.yobsubcord.getSQLConnection
import java.io.StringReader
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.xml.parsers.DocumentBuilderFactory

@RestController
class Notification {
    val logger: Logger = LoggerFactory.getLogger(Notification::class.java)
    val notificationLogger: Logger = LoggerFactory.getLogger("xyz.miyayu.yobsub.notlog")

    @PostMapping("hub")
    fun postNotification(@RequestBody body: String): ResponseEntity<String> {
        try {
            val inputSource = InputSource(StringReader(body))

            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            val document = db.parse(inputSource)
            document.documentElement.normalize()
            val feed = document.documentElement

            val nodeMap = getChildNodeMaps(feed)

            //投稿なら
            if (nodeMap.containsKey("entry")) {
                logger.info("-投稿-")
                val entryMap = getChildNodeMaps(nodeMap["entry"]!!)
                onPost(entryMap,body)
            }

            //削除なら
            else if (nodeMap.containsKey("at:deleted-entry")) {
                logger.info("-削除-")
                val deletedEntry = nodeMap["at:deleted-entry"]!!
                val ref = (deletedEntry as Element).getAttribute("ref")
                val videoId = ref.split(":")[2]
                notificationLogger.info("removed: $videoId")


            }
            //それ以外なら
            else {
                logger.warn("不明なDOM")
                logger.info(body)
                return ResponseEntity("Unknown data", HttpStatus.BAD_REQUEST)
            }
            return ResponseEntity("", HttpStatus.OK)
        } catch (ex: Exception) {
            notificationLogger.warn("Notification Error!!")
            notificationLogger.error(ex.stackTraceToString())
            //こちらで処理できていないだけなのでokを返す
            return ResponseEntity("", HttpStatus.OK)
        }
    }

    /**
     * 環境変数にチャンネルidが含まれているか
     *
     * @param id チャンネルid
     * @return return 環境変数に含まれているか
     */
    fun isApprovalChannel(id: String): Boolean {
        return EnvWrapper.YTCHANNELS.contains(id)
    }

    fun onPost(entryMap: Map<String,Node>,body: String): ResponseEntity<String>{
        val videoId = entryMap["yt:videoId"]!!.textContent
        val channelId = entryMap["yt:channelId"]!!.textContent

        //環境変数で定義されていないチャンネルなら
        if (!isApprovalChannel(channelId)) {
            notificationLogger.warn("許可されていないチャンネル")
            logger.info(body)
            return ResponseEntity("Not Approval Channel", HttpStatus.BAD_REQUEST)
        }

        //データベース上に存在するのか確認
        val isExists: Boolean = isExistsOnDatabase(videoId)

        if(isExists){
            //TODO 存在するときの処理
            return ResponseEntity("hoge",HttpStatus.OK)
        }

        //API経由でVideoを取得
        val video = getVideo(videoId)

        //現在の日時(UTCを取得)
        val nowLocalDateTime = LocalDateTime.now(ZoneId.of("UTC"))

        val videoTime = video.videoStatus.run {
            if (this == VideoStatus.PRE_LIVE)
                return@run video.scheduledTime!!
            else {
                return@run video.datetime
            }
        }

        //差分(分を取得)
        val diff = ChronoUnit.MINUTES.between(videoTime, nowLocalDateTime)
        //24時間以上差が空いているならエラーを返す
        if (60 * 24 < diff) {
            throw Exception("24 hour Error!! + $diff")
        }

        //データベースに追加する
        getSQLConnection().use{
            val pstmt =
                it.prepareStatement("INSERT INTO videos VALUES(?,?,?,?,?,?)")
            pstmt.setString(1,videoId)
            pstmt.setString(2,channelId)
            pstmt.setString(3,video.videoTitle)
            pstmt.setInt(4,video.videoStatus.dataValue)
            pstmt.setString(5,toDateString(nowLocalDateTime))
            if(video.scheduledTime == null){
                pstmt.setNull(6, Types.STRING)
            }else{
                pstmt.setString(6,toDateString(video.scheduledTime))
            }
            pstmt.executeUpdate()
        }

        alert(video)


        notificationLogger.info("video: $videoId, channel: $channelId diff: $diff exist: $isExists")
        return ResponseEntity("",HttpStatus.OK)
    }
    fun isExistsOnDatabase(videoId: String):Boolean{
        try{
            getSQLConnection().use{
                val pstmt =
                    it.prepareStatement("SELECT COUNT(videoId) as CNT FROM videos WHERE videoId= ? GROUP BY videoId")
                pstmt.setString(1, videoId)
                val result = pstmt.executeQuery()
                return result.next() && result.getInt("CNT") == 1
            }
        }catch(e:Exception){
            throw e
        }
    }
    fun toDateString(localDateTime: LocalDateTime):String{
        return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(localDateTime)
    }
}
package xyz.miyayu.yobsub.yobsubcord.pubsub

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
import xyz.miyayu.yobsub.yobsubcord.getChildNodeMaps
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

@RestController
class Notification {
    val logger = LoggerFactory.getLogger(Notification::class.java)
    val notificationLogger = LoggerFactory.getLogger("xyz.miyayu.yobsub.notlog")
    @PostMapping("hub")
    fun postNotification(@RequestBody body: String): ResponseEntity<String>{
        val inputSource = InputSource(StringReader(body))

        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val document = db.parse(inputSource)
        document.documentElement.normalize()
        val feed = document.documentElement

        val nodeMap = getChildNodeMaps(feed)
        
        //投稿なら
        if(nodeMap.containsKey("entry")){
            logger.info("-投稿-")
            val entryMap = getChildNodeMaps(nodeMap["entry"]!!)
            val videoId = entryMap["yt:videoId"]!!.textContent
            val channelId = entryMap["yt:channelId"]!!.textContent

            //環境変数で定義されていないチャンネルなら
            if(!isApprovalChannel(channelId)){
                notificationLogger.warn("許可されていないチャンネル")
                logger.info(body)
                return ResponseEntity("Not Approval Channel",HttpStatus.BAD_REQUEST)
            }
            notificationLogger.info("video: $videoId, channel: $channelId")
        }
        //削除なら
        else if(nodeMap.containsKey("at:deleted-entry")){
            logger.info("-削除-")
            val deletedEntry = nodeMap["at:deleted-entry"]!!
            val ref = (deletedEntry as Element).getAttribute("ref")
            val videoId = ref.split(":")[2]
            notificationLogger.info("removed: $videoId")


        }
        //それ以外なら
        else{
            logger.warn("不明なDOM")
            logger.info(body)
            return ResponseEntity("Unknown data",HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("",HttpStatus.OK)
    }

    /**
     * 環境変数にチャンネルidが含まれているか
     *
     * @param id チャンネルid
     * @return return 環境変数に含まれているか
     */
    fun isApprovalChannel(id:String):Boolean{
        return EnvWrapper.YTCHANNELS.contains(id)
    }
}
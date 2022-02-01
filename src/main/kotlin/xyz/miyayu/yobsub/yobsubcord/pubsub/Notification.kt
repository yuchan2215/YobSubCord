package xyz.miyayu.yobsub.yobsubcord.pubsub

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

@RestController
class Notification {
    val logger = LoggerFactory.getLogger(Notification::class.java)
    val notificationLogger = LoggerFactory.getLogger("xyz.miyayu.yobsub.notlog")
    @PostMapping("hub")
    fun postNotification(@RequestBody body: String){
        val inputSource = InputSource(StringReader(body))

        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val document = db.parse(inputSource)
        document.documentElement.normalize()
        val feed = document.documentElement

        //処理を走ったかどうか確認する
        var runned = false

        val nodeList = feed.childNodes
        for(i in 0 until nodeList.length){
            val node = nodeList.item(i)
            //投稿なら
            if(node.nodeName.equals("entry") && node.nodeType == Node.ELEMENT_NODE){
                logger.info("-投稿-")
                runned = true
                break
            }
            //削除なら
            if(node.nodeName.equals("at:deleted-entry") && node.nodeType == Node.ELEMENT_NODE){
                logger.info("-削除-")
                runned = true
                break
            }
        }
        if(!runned){
            logger.warn("不明なDOM")
            logger.info(body)
        }
    }
}
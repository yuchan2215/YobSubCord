package xyz.miyayu.yobsub.yobsubcord.pubsub

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Notification {
    val logger = LoggerFactory.getLogger(Notification::class.java)
    val notificationLogger = LoggerFactory.getLogger("xyz.miyayu.yobsub.notlog")
    @PostMapping("hub")
    fun postNotification(@RequestBody body: String){
        logger.info(body)
    }
}
package xyz.miyayu.yobsub.yobsubcord.pubsub

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper

@RestController
class Challenge {

    val logger = LoggerFactory.getLogger(Challenge::class.java)
    val weblogger = LoggerFactory.getLogger("xyz.miyayu.yobsub.netlog")
    @GetMapping("hub")
    fun getChallenge(
        @RequestParam params:Map<String,String>
    ) : ResponseEntity<String> {
        /**
        weblogger.info("--Challenge Start--")
        params.forEach{
            weblogger.info(it.key + ":" + it.value)
        }**/
        val hubMode = params.getOrDefault("hub.mode","")
        val hubVerifyToken = params.getOrDefault("hub.verify_token","")
        val hubChallenge = params.getOrDefault("hub.challenge","")
        return if(hubMode == "subscribe" || hubMode == "unsubscribe"){
            if(hubVerifyToken == EnvWrapper.TOKEN){
                weblogger.info("--CHALLENGE OK--")
                ResponseEntity<String>(hubChallenge,HttpStatus.OK)
            }else{
                weblogger.info("--CHALLENGE ERROR--")
                ResponseEntity<String>("token unmatch", HttpStatus.BAD_REQUEST)
            }
        }else{
            weblogger.info("--CHALLENGE ERROR--")
            ResponseEntity<String>("HTTP/1.1 404 Not Found", HttpStatus.BAD_REQUEST)
        }
    }
}
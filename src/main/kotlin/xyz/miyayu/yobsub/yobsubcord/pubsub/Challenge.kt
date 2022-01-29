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
    @GetMapping("hub")
    fun getChallenge(
        @RequestParam(name = "hub_mode", required = true)hub_mode: String,
        @RequestParam(name = "hub_challenge", required = true)hub_challenge:String,
        @RequestParam(name = "hub_verify_token", required = true)hub_verify_token:String
    ) : ResponseEntity<String> {
        logger.info("Challenge Start\nMode: %s\nchallenge: %s\nverify: %s".format(hub_mode,hub_challenge,hub_verify_token))
        return if(hub_mode == "subscribe" || hub_mode == "unsubscribe"){
            if(hub_verify_token == EnvWrapper.TOKEN){
                ResponseEntity<String>(hub_challenge,HttpStatus.OK)
            }else{
                ResponseEntity<String>("token unmatch",HttpStatus.NOT_FOUND)
            }
        }else{
            ResponseEntity<String>("HTTP/1.1 404 Not Found", HttpStatus.NOT_FOUND)
        }
    }
}
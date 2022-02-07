package xyz.miyayu.yobsub.yobsubcord

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @RequestMapping("/")
    fun test(): String {
        return "Hello World"
    }
}
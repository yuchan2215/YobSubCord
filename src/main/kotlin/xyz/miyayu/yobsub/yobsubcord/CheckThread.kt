package xyz.miyayu.yobsub.yobsubcord

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.miyayu.yobsub.yobsubcord.api.getVideos
import xyz.miyayu.yobsub.yobsubcord.pubsub.Notification
import java.util.*

class CheckThread :Thread(){
    val logger: Logger = LoggerFactory.getLogger(CheckThread::class.java)
    override fun run(){
        Timer().schedule(object: TimerTask(){
            override fun run() {
                dbCheck()
                apiCheck()
            }
        },0,1000*60 + 500)
    }
    private fun dbCheck(){
        try {
            getSQLConnection().use { it ->
                val pstmt =
                    it.prepareStatement("SELECT videoId FROM videos WHERE videoStatus = 1")
                val result = pstmt.executeQuery()
                val checkList = mutableListOf<String>()
                while(result?.next() == true){
                    checkList.add(result.getString("videoId"))
                }
                pstmt.close()
                checkList.forEach { n ->
                    logger.info("Checking... $n")
                    Notification().onPost(n)

                }
            }
        }catch(e:Exception){
            logger.error(e.stackTraceToString())
        }

    }
    private fun apiCheck(){
        try {
            var checks = 0
            var errors = 0
            EnvWrapper.YTCHANNELS.forEach {
                getVideos(it).forEach { videoid ->
                    checks++
                    try {
                        Notification().onPost(videoid)
                    } catch (_: Exception) {
                        errors++
                    }
                }
            }
            logger.info("$checks 件のデータを確認しました")
        }catch(e:Exception){
            logger.error(e.stackTraceToString())
        }
    }
}
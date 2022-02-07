package xyz.miyayu.yobsub.yobsubcord

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.miyayu.yobsub.yobsubcord.pubsub.Notification
import java.util.*

class CheckThread :Thread(){
    val logger: Logger = LoggerFactory.getLogger(CheckThread::class.java)
    override fun run(){
        Timer().schedule(object: TimerTask(){
            override fun run() {
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
        },0,1000*60)
    }
}
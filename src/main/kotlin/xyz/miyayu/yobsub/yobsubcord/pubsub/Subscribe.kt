package xyz.miyayu.yobsub.yobsubcord.pubsub

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

    private val logger: Logger = LoggerFactory.getLogger("subscribe")

    fun subscribe() {
        EnvWrapper.YTCHANNELS.forEach{sendPostRequest(it)}
    }

    fun sendPostRequest(channelId: String) {

        // POST パラメーターの定義 //
        val callbackURL = "https://${EnvWrapper.URL}/hub"
        val topic = "https://www.youtube.com/xml/feeds/videos.xml?channel_id=$channelId"
        val verifyToken = EnvWrapper.TOKEN
        val param =
            "hub.callback=$callbackURL&" +
            "hub.topic=$topic&" +
            "hub.verify=async&" +
            "hub.mode=subscribe&" +
            "hub.verify_token=$verifyToken"

        logger.info("Sending Request... $param")

        // POST //
        try {
            val url = URL("https://pubsubhubbub.appspot.com/subscribe")
            val connection = url.openConnection() as HttpURLConnection
            connection.doOutput = true
            connection.requestMethod = "POST"
            connection.connect()

            val os = connection.outputStream
            val writer = BufferedWriter(OutputStreamWriter(os,"UTF-8"))
            writer.write(param)
            writer.flush()
            writer.close()


            val statusCode = connection.responseCode
            logger.info("Code:$statusCode")

        } catch (exception: Exception) {
            logger.error(exception.stackTraceToString())
        }
    }



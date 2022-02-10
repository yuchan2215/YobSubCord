package xyz.miyayu.yobsub.yobsubcord.rest

import org.json.JSONArray
import org.json.JSONObject
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.miyayu.yobsub.yobsubcord.getSQLConnection


@RestController
class ItemsController {
    @RequestMapping("/videos")
    fun videos(): String{
        getSQLConnection().use{
            val rs = it.prepareStatement("SELECT * FROM videos ORDER BY lastLook DESC LIMIT 3").executeQuery()
            val jsonObj = JSONObject()
            val jsonAry = JSONArray()
            while(rs?.next() == true){
                val itemObj = JSONObject()
                val videoId = rs.getString("videoId")
                val channelId = rs.getString("channelId")
                val videoStatus = rs.getString("videoStatus")
                val lastLook = rs.getString("lastLook")

                itemObj.put("videoId",videoId)
                itemObj.put("channelId",channelId)
                itemObj.put("videoStatus",videoStatus)
                itemObj.put("lastLook",lastLook)
                jsonAry.put(itemObj)
            }
            jsonObj.put("items",jsonAry)
            return jsonObj.toString()
        }
    }
}
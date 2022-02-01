package xyz.miyayu.yobsub.yobsubcord

import org.w3c.dom.Node

fun getChildNodeMaps(node:Node):Map<String,Node>{
    val map = mutableMapOf<String,Node>()
    for(i in 0 until node.childNodes.length){
        map[node.childNodes.item(i).nodeName] = node.childNodes.item(i)
    }
    return map
}
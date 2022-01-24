package xyz.miyayu.yobsub.yobsubcord.discord

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class PushButton:ListenerAdapter() {
    override fun onButtonClick(event: ButtonClickEvent) {
        //Live-Alert
        if(event.button?.id.let{it == "on_mention"}){
            liveAlert((event))
        }
        //Live-Alert(DM)
        else if(event.button?.id.let{it == "on_dm"}){
            liveAlertDm(event)
        }
        //解除
        else if(event.button?.id.let{it == "clear"}){
            removeRoles(event)
        }
    }
    fun liveAlert(event: ButtonClickEvent){

    }
    fun liveAlertDm(event: ButtonClickEvent){

    }
    fun removeRoles(event: ButtonClickEvent){

    }
}
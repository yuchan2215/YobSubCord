package xyz.miyayu.yobsub.yobsubcord.discord

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.Button

class Click:ListenerAdapter() {
    val GETREMOVE = "次のロールが付与/剥奪されます"
    val GETONLY = "次のロールが付与されます"
    val REMOVEONLY = "次のロールが剥奪されます"
    override fun onSlashCommand(event: SlashCommandEvent) {
        if(event.commandIdLong != 933602268278841354) return
        val role = event.getOption("workrole")?.asRole ?: throw Exception(NullPointerException())
        val message = event.getOption("message")?.asString ?: ""
        val canRemove = event.getOption("canremove")?.asBoolean ?: true
        val canGet = event.getOption("canget")?.asBoolean ?: true
        val getText = event.getOption("gettext")?.asString ?: "取得する"
        val removeText = event.getOption("removetext")?.asString ?: "削除する"
        event.jda.retrieveCommands().complete()[0]

        //両方falseなら
        if(!canRemove && !canGet){
            event.reply("取得も剥奪もできないものは作成できません").setEphemeral(true).queue()
            return
        }
        val fieldTitle =
            if(canGet && canRemove){
                GETREMOVE
            }else if (canGet){
                GETONLY
            }else{
                REMOVEONLY
            }

        val embed = EmbedBuilder()
            .setDescription(message)
            .addField(fieldTitle,role.asMention,true)

        val action = event.replyEmbeds(embed.build())
        if(canGet){
            val getid = "get-" + role.id
            val button = Button.primary(getid,getText)
            action.addActionRow(button)
        }
        if(canRemove){
            val removeid = "remove-" + role.id
            val button = Button.danger(removeid,removeText)
            action.addActionRow(button)
        }
        action.queue()
    }
}
package xyz.miyayu.yobsub.yobsubcord.discord.commands

import groovy.lang.Binding
import groovy.lang.GroovyShell
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.miyayu.yobsub.yobsubcord.YobSubCordApplication
import xyz.miyayu.yobsub.yobsubcord.discord.JDAWrapper
import java.lang.reflect.Modifier

class Eval : ListenerAdapter(){
    private val logger :Logger = LoggerFactory.getLogger(YobSubCordApplication::class.java)
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if(event.author.id != "192924983872192512")return
        if(!event.message.contentRaw.startsWith("tb.eval "))return

        val content = event.message.contentRaw
            .replace(Regex("^tb.eval "),"")
            .replace("\n","")
        try{
            val result: Any? = eval(content,event.message)
            val generics:String = if (result != null)
                Modifier.toString(result.javaClass.modifiers)
                else "<?>"
            val type:String = if (result != null)
                result.javaClass.canonicalName
                else "null"
            event.message.reply("Result[%s](%s)%s".format(generics,type,result)).queue()
        }catch(e:Exception){
            val clazz = e.javaClass.simpleName
            val message = e.message
            event.message.reply("Error: %s : %s".format(clazz,message)).queue()
            e.stackTrace
        }
    }
    private fun eval(expression: String,message: Message):Any?{
        val b = Binding()
        b.setVariable("logger",logger)
        b.setVariable("jda",JDAWrapper.getJDA())
        b.setVariable("message",message)
        b.setProperty("logger",logger)
        b.setProperty("jda",JDAWrapper.getJDA())
        b.setProperty("message",message)

        /**TEST AREA**/

        /**END TEST AREA**/

        val sh = GroovyShell(b)
        return sh.evaluate(expression)
    }

}
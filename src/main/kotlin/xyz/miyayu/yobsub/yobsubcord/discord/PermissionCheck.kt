package xyz.miyayu.yobsub.yobsubcord.discord

import net.dv8tion.jda.api.entities.Member
import xyz.miyayu.yobsub.yobsubcord.EnvWrapper

fun checkPermission(member: Member): Boolean {
    //Adminロールを持っているかどうか
    if (member.roles.contains(
            member.jda.getRoleById(EnvWrapper.ADMIN_ROLE)
        )
    ) return true

    //デバッグユーザーかどうか
    return member.id == EnvWrapper.DEBUG_USER
}
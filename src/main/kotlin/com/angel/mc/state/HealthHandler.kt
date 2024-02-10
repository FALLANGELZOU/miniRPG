package com.angel.mc.state

import com.angel.mc.MiniRPG
import com.angel.mc.dao.PlayerMapper
import com.angel.mc.dao.allInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator
import com.mybatisflex.core.row.Db
import com.mybatisflex.kotlin.extensions.db.mapper
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player
import java.util.*

class HealthHandler {
    fun setMaxHealthLevel(player: Player, level: Int) {
        Db.tx {
            val playerInfo = player.allInfo()
            val healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
            playerInfo?.health?.let {
                healthAttribute?.let { healthAttribute ->
//                    println(level-it)
                    val opUuid = UUID.randomUUID()
                    healthAttribute.addModifier(AttributeModifier(
                        opUuid.toString(),
                        (level - it).toDouble(),
                        AttributeModifier.Operation.ADD_NUMBER
                    ))
                    playerInfo.health = level
//                    val saveResult = playerInfo.updateById()    //  不知道为什么没更新
                    val saveResult = mapper<PlayerMapper>().update(playerInfo)
                    if(saveResult == 0) {
                        MiniRPG.ktProxy.info("[setMaxHealthLevel] 执行失败！")
                        healthAttribute.removeModifier(opUuid)
                        return@tx false
                    }
                }
            } ?: return@tx false

            return@tx false
        }

    }

    fun getMaxHealth(player: Player): Double? {
        return player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value
    }
}
package com.angel.mc.listener

import com.angel.mc.MiniRPG
import com.angel.mc.dao.MoneyTable
import com.angel.mc.dao.PlayerTable
import com.mybatisflex.core.row.Db
import com.mybatisflex.kotlin.extensions.db.filter
import com.mybatisflex.kotlin.extensions.kproperty.`in`
import com.mybatisflex.kotlin.extensions.mapper.insert
import net.kyori.adventure.inventory.Book
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class CommonListener: Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        registerAccount(player)

    }


    private fun registerAccount(player: Player) {
        Db.tx {
            val players = filter<PlayerTable> {
                PlayerTable::uuid.`in`(player.uniqueId.toString())
            }
            if (players.isEmpty()) {
                val playerTable = PlayerTable().also {
                    it.uuid = player.uniqueId.toString()
                }
                playerTable.insert()
                val moneyTable = MoneyTable().also {
                    it.money = 0
                    it.playerId = playerTable.id
                }
                moneyTable.insert()


            }
            return@tx true
        }
    }

}
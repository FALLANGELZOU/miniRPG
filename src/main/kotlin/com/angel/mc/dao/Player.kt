package com.angel.mc.dao

import com.mybatisflex.annotation.Id
import com.mybatisflex.annotation.KeyType
import com.mybatisflex.annotation.RelationOneToOne
import com.mybatisflex.annotation.Table
import com.mybatisflex.core.BaseMapper
import com.mybatisflex.core.activerecord.MapperModel
import com.mybatisflex.core.query.QueryWrapper
import com.mybatisflex.kotlin.extensions.db.mapper
import com.mybatisflex.kotlin.extensions.kproperty.eq
import org.bukkit.entity.Player


@Table("t_player")
class PlayerTable: MapperModel<PlayerTable> {
    @Id(keyType = KeyType.Auto) var id: Int = -1
    var uuid: String? = null
    var health: Int? = 0


    @RelationOneToOne(targetField = "playerId")
    var money: MoneyTable? = null
}


interface PlayerMapper : BaseMapper<PlayerTable?>

fun Player.allInfo(): PlayerTable? {
    return mapper<PlayerMapper>()
        .selectOneWithRelationsByQuery(
            QueryWrapper().where(PlayerTable::uuid eq uniqueId.toString())
        )
}
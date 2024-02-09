package com.angel.mc.dao

import com.angel.mc.economic.Money
import com.mybatisflex.annotation.Id
import com.mybatisflex.annotation.KeyType
import com.mybatisflex.annotation.RelationOneToOne
import com.mybatisflex.annotation.Table
import com.mybatisflex.core.BaseMapper
import com.mybatisflex.core.activerecord.MapperModel
import com.sun.jna.platform.win32.Advapi32Util.Account


@Table("t_player")
class PlayerTable: MapperModel<PlayerTable> {
    @Id(keyType = KeyType.Auto) var id: Int = -1
    var uuid: String? = null


    @RelationOneToOne(targetField = "playerId")
    var money: MoneyTable? = null
}


interface PlayerMapper : BaseMapper<PlayerTable?>


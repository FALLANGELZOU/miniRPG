package com.angel.mc.dao

import com.mybatisflex.annotation.Id
import com.mybatisflex.annotation.KeyType
import com.mybatisflex.annotation.RelationOneToOne
import com.mybatisflex.annotation.Table
import com.mybatisflex.core.BaseMapper
import com.mybatisflex.core.activerecord.MapperModel

@Table("t_money")
class MoneyTable: MapperModel<MoneyTable> {
    @Id(keyType = KeyType.Auto) var id: Int = -1
    var money: Float? = 0f
    var playerId: Int? = -1
}

interface MoneyMapper : BaseMapper<MoneyTable?>
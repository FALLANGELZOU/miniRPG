package com.angel.mc.dao

import com.mybatisflex.annotation.Id
import com.mybatisflex.annotation.Table
import com.mybatisflex.core.BaseMapper
import com.sun.jna.platform.win32.Advapi32Util.Account


@Table("t_player")
data class PlayerTable(
    @Id var id: Int = -1,
    var uuid: String?
)


interface PlayerMapper : BaseMapper<PlayerTable?>
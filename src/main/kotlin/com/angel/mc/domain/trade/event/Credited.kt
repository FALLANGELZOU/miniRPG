package com.angel.mc.domain.trade.event

import com.angel.mc.domain.common.DomainEvent
import com.angel.mc.domain.trade.Money
import java.time.LocalDateTime

/**
 * @description 已入账
 * @author eamon
 * @date 2024/2/12
 */
class Credited(
    val uid: String,
    val amount: Money,
    val occurTime: LocalDateTime
) : DomainEvent
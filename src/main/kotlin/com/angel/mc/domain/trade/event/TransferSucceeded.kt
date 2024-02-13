package com.angel.mc.domain.trade.event

import com.angel.mc.domain.common.DomainEvent
import com.angel.mc.domain.trade.Money
import java.time.LocalDateTime

/**
 * @description 转账成功
 * @author eamon
 * @date 2024/2/12
 */
class TransferSucceeded(
    val from: String,
    val to: String,
    val amount: Money,
    val occurTime: LocalDateTime
) : DomainEvent
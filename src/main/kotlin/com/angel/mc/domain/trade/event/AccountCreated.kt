package com.angel.mc.domain.trade.event

import com.angel.mc.domain.common.DomainEvent
import java.time.LocalDateTime

/**
 * @description 已开户
 * @author eamon
 * @date 2024/2/12
 */
class AccountCreated(
    val uid: String,
    val occurTime: LocalDateTime
) : DomainEvent

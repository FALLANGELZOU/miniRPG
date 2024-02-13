package com.angel.mc.domain.trade.event

import com.angel.mc.domain.common.DomainEvent
import java.time.LocalDateTime

/**
 * @description 账户已解冻
 * @author eamon
 * @date 2024/2/12
 */
class AccountUnfrozen(
    val uid: String,
    val occurTime: LocalDateTime
) : DomainEvent
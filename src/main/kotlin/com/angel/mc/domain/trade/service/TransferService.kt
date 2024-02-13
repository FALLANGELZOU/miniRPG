package com.angel.mc.domain.trade.service

import com.angel.mc.domain.trade.Account
import com.angel.mc.domain.trade.Money

/**
 * @description 转账服务
 * @author eamon
 * @date 2024/2/12
 */
fun transfer(from: Account, to: Account, amount: Money): Boolean {
    from.debit(amount)
    to.credit(amount)
    return true
}

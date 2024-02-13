package com.angel.mc.domain.trade.repository

import com.angel.mc.domain.trade.Account

/**
 * @description 账号仓储
 * @author eamon
 * @date 2024/2/12
 */
interface AccountRepository {
    fun findByUserId(userId: String): Account
    fun save(account: Account)
}
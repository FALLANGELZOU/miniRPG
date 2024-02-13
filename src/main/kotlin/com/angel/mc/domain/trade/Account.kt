package com.angel.mc.domain.trade

import com.angel.mc.domain.common.DomainException

/**
 * @description 账号
 * @author eamon
 * @date 2024/2/12
 */
class Account(
    private var uid: String,
    private var status: AccountStatus,
    private var balance: Money
) {
    constructor(uid: String) : this(uid, AccountStatus.OPEN, zero())

    fun getUid(): String = uid

    fun debit(amount: Money): Boolean {
        if (AccountStatus.OPEN != status) {
            throw DomainException("账户已冻结")
        }
        if (amount > balance) {
            throw DomainException("余额不足")
        }
        balance -= amount
        return true
    }

    fun credit(amount: Money): Boolean {
        if (AccountStatus.OPEN != status) {
            throw DomainException("账户已冻结")
        }
        balance += amount
        return true
    }

    fun freeze(): Boolean {
        status = AccountStatus.BLOCK
        return true
    }

    fun unfreeze(): Boolean {
        status = AccountStatus.OPEN
        return true
    }
}
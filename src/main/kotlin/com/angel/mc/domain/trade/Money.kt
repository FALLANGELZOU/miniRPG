package com.angel.mc.domain.trade

/**
 * @description 钱
 * @author eamon
 * @date 2024/2/9
 */
class Money(
    private val amount: Long
) {
    operator fun compareTo(another: Money): Int {
        return this.amount.compareTo(another.amount)
    }

    operator fun compareTo(another: Int): Int {
        return this.amount.compareTo(another)
    }

    operator fun plusAssign(another: Money) {
        this.amount + another.amount
    }

    operator fun minusAssign(another: Money) {
        this.amount - another.amount
    }
}

fun zero(): Money {
    return Money(0)
}
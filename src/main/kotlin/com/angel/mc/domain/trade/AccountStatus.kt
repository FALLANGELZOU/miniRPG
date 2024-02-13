package com.angel.mc.domain.trade

/**
 * @description 账号状态枚举
 * @author eamon
 * @date 2024/2/12
 */
enum class AccountStatus(
    val code: Int,
    val label: String
) {
    BLOCK(0, "冻结"),
    OPEN(1, "未冻结")
}
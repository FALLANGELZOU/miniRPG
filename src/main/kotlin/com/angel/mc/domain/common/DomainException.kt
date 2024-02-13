package com.angel.mc.domain.common

/**
 * @description 业务异常
 * @author eamon
 * @date 2024/2/12
 */
class DomainException(
    msg: String
) : RuntimeException(msg)
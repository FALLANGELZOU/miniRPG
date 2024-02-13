package com.angel.mc.domain.common

/**
 * @description 事件队列
 * @author eamon
 * @date 2024/2/13
 */
interface EventQueue {
    fun enqueue(event: DomainEvent)

    fun queue(): List<DomainEvent>
}
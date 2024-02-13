package com.angel.mc.domain.common

/**
 * @description 命令处理器
 * @author eamon
 * @date 2024/2/6
 */
interface CommandHandler<T : Command> {
    fun handle(eventQueue: EventQueue, command: T)
}
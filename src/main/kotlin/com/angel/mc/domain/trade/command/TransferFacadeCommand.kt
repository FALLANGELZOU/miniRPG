package com.angel.mc.domain.trade.command

import com.angel.mc.domain.common.Command
import com.angel.mc.domain.common.CommandHandler
import com.angel.mc.domain.common.DomainException
import com.angel.mc.domain.common.EventQueue
import com.angel.mc.domain.trade.Account
import com.angel.mc.domain.trade.Money
import com.angel.mc.domain.trade.event.TransferSucceeded
import com.angel.mc.domain.trade.repository.AccountRepository
import com.angel.mc.domain.trade.service.transfer
import java.time.LocalDateTime

/**
 * @description 转账
 * @author eamon
 * @date 2024/2/6
 */
class TransferFacadeCommand(
    val from: String,
    val to: String,
    val amount: Money
) : Command

class TransferFacadeCommandHandler(
    private val accountRepository: AccountRepository
) : CommandHandler<TransferFacadeCommand> {
    override fun handle(eventQueue: EventQueue, command: TransferFacadeCommand) {
        if (command.amount <= 0) {
            throw DomainException("转账金额必须大于0")
        }
        var from: Account? = accountRepository.findByUserId(command.from)
        var to: Account? = accountRepository.findByUserId(command.to)
        if (from == null || to == null) {
            throw DomainException("账户不存在")
        }
        transfer(from, to, command.amount)
        eventQueue.enqueue(
            TransferSucceeded(
                from.getUid(),
                to.getUid(),
                command.amount,
                LocalDateTime.now()
            )
        )
    }
}
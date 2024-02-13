package com.angel.mc.domain.trade.command

import com.angel.mc.domain.common.Command
import com.angel.mc.domain.common.CommandHandler
import com.angel.mc.domain.common.DomainException
import com.angel.mc.domain.common.EventQueue
import com.angel.mc.domain.trade.Account
import com.angel.mc.domain.trade.Money
import com.angel.mc.domain.trade.event.Debited
import com.angel.mc.domain.trade.repository.AccountRepository
import java.time.LocalDateTime

/**
 * @description 支出
 * @author eamon
 * @date 2024/2/6
 */
class DebitCommand(
    val uid: String,
    val amount: Money
) : Command

class DebitCommandHandler(
    private val accountRepository: AccountRepository
) : CommandHandler<DebitCommand> {
    override fun handle(eventQueue: EventQueue, command: DebitCommand) {
        if (command.amount <= 0) {
            throw DomainException("支出金额必须大于0")
        }
        var account: Account? = accountRepository.findByUserId(command.uid)
        if (account == null) {
            throw DomainException("用户不存在")
        }
        account.debit(command.amount)
        accountRepository.save(account)
        eventQueue.enqueue(
            Debited(
                account.getUid(),
                command.amount,
                LocalDateTime.now()
            )
        )
    }
}
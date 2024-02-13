package com.angel.mc.domain.trade.command

import com.angel.mc.domain.common.Command
import com.angel.mc.domain.common.CommandHandler
import com.angel.mc.domain.common.DomainException
import com.angel.mc.domain.common.EventQueue
import com.angel.mc.domain.trade.Account
import com.angel.mc.domain.trade.Money
import com.angel.mc.domain.trade.event.Credited
import com.angel.mc.domain.trade.repository.AccountRepository
import java.time.LocalDateTime

/**
 * @description 入账
 * @author eamon
 * @date 2024/2/6
 */
class CreditCommand(
    val uid: String,
    val amount: Money
) : Command

class CreditCommandHandler(
    private val accountRepository: AccountRepository
) : CommandHandler<CreditCommand> {
    override fun handle(eventQueue: EventQueue, command: CreditCommand) {
        if (command.amount <= 0) {
            throw DomainException("入账金额必须大于0")
        }
        var account: Account? = accountRepository.findByUserId(command.uid)
        if (account == null) {
            throw DomainException("用户不存在")
        }
        account.credit(command.amount)
        accountRepository.save(account)
        eventQueue.enqueue(
            Credited(
                account.getUid(),
                command.amount,
                LocalDateTime.now()
            )
        )
    }
}
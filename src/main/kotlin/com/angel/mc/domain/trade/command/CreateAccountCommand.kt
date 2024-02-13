package com.angel.mc.domain.trade.command

import com.angel.mc.domain.common.Command
import com.angel.mc.domain.common.CommandHandler
import com.angel.mc.domain.common.DomainException
import com.angel.mc.domain.common.EventQueue
import com.angel.mc.domain.trade.Account
import com.angel.mc.domain.trade.event.AccountCreated
import com.angel.mc.domain.trade.repository.AccountRepository
import java.time.LocalDateTime

/**
 * @description 开户
 * @author eamon
 * @date 2024/2/6
 */
class CreateAccountCommand(
    val uid: String
) : Command

class CreateAccountCommandHandler(
    private val accountRepository: AccountRepository
) : CommandHandler<CreateAccountCommand> {
    override fun handle(eventQueue: EventQueue, command: CreateAccountCommand) {
        var account: Account? = accountRepository.findByUserId(command.uid)
        if (account != null) {
            throw DomainException("用户已开户")
        }
        account = Account(command.uid)
        accountRepository.save(account)
        eventQueue.enqueue(
            AccountCreated(
                account.getUid(),
                LocalDateTime.now()
            )
        )
    }
}
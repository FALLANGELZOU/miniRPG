package com.angel.mc.domain.trade.command

import com.angel.mc.domain.common.Command
import com.angel.mc.domain.common.CommandHandler
import com.angel.mc.domain.common.DomainException
import com.angel.mc.domain.common.EventQueue
import com.angel.mc.domain.trade.Account
import com.angel.mc.domain.trade.event.AccountFrozen
import com.angel.mc.domain.trade.repository.AccountRepository
import java.time.LocalDateTime

/**
 * @description 冻结账户
 * @author eamon
 * @date 2024/2/6
 */
class FreezeAccountCommand(
    val uid: String
) : Command

class FreezeAccountCommandHandler(
    private val accountRepository: AccountRepository
) : CommandHandler<FreezeAccountCommand> {
    override fun handle(eventQueue: EventQueue, command: FreezeAccountCommand) {
        var account: Account? = accountRepository.findByUserId(command.uid)
        if (account == null) {
            throw DomainException("用户不存在")
        }
        account.freeze()
        accountRepository.save(account)
        eventQueue.enqueue(
            AccountFrozen(
                account.getUid(),
                LocalDateTime.now()
            )
        )
    }
}
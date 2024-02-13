package com.angel.mc.domain.trade.command

import com.angel.mc.domain.common.Command
import com.angel.mc.domain.common.CommandHandler
import com.angel.mc.domain.common.DomainException
import com.angel.mc.domain.common.EventQueue
import com.angel.mc.domain.trade.Account
import com.angel.mc.domain.trade.event.AccountUnfrozen
import com.angel.mc.domain.trade.repository.AccountRepository
import java.time.LocalDateTime

/**
 * @description 解冻账户
 * @author eamon
 * @date 2024/2/6
 */
class UnfreezeAccountCommand(
    val uid: String
) : Command

class UnfreezeAccountCommandHandler(
    private val accountRepository: AccountRepository
) : CommandHandler<UnfreezeAccountCommand> {
    override fun handle(eventQueue: EventQueue, command: UnfreezeAccountCommand) {
        var account: Account? = accountRepository.findByUserId(command.uid)
        if (account == null) {
            throw DomainException("用户不存在")
        }
        account.unfreeze()
        accountRepository.save(account)
        eventQueue.enqueue(
            AccountUnfrozen(
                account.getUid(),
                LocalDateTime.now()
            )
        )
    }
}
package com.angel.mc.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import org.bukkit.command.CommandSender

@CommandAlias("minirpg|mr")
class MRCommand: BaseCommand() {
    @Default
    fun onDefault(sender: CommandSender) {
        sender.sendMessage("这是默认消息！")
    }
}
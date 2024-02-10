package com.angel.mc.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import com.angel.mc.menu.openMenu
import com.angel.mc.state.StateManager
import dev.triumphteam.gui.guis.Gui
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("minirpg|mr")
class MRCommand: BaseCommand() {
    @Default
    fun onDefault(sender: CommandSender) {
        sender.sendMessage("这是默认消息！")
    }

    @Subcommand("menu|m")
    fun onMenu(player: Player) {
        player.openMenu()
    }

    @Subcommand("state|s")
    inner class StateCommand: BaseCommand() {
        @Subcommand("setHealthLevel")
        fun setMaxHealth(player:Player, level: Int) {
            StateManager.healthHandler.setMaxHealthLevel(player, level)
        }
    }
}
package com.angel.mc

import co.aikar.commands.BukkitCommandManager
import co.aikar.commands.CommandManager
import com.angel.mc.command.MRCommand
import com.angel.mc.configuration.Configuration
import com.angel.mc.database.DataBase
import com.angel.mc.listener.CommonListener
import com.mybatisflex.core.MybatisFlexBootstrap

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import kotlin.math.log

class MiniRPGProxy() {
    lateinit var plugin: JavaPlugin
    lateinit var commandManager: BukkitCommandManager
    lateinit var bootstrap: MybatisFlexBootstrap
    lateinit var logger: Logger
    private lateinit var database: DataBase

    fun bind(plugin: JavaPlugin) {
        this.plugin = plugin
    }
    fun info(str: String) {
        logger.info(str)
    }
    fun onEnable() {
        logger = plugin.server.logger
        commandManager = BukkitCommandManager(plugin)
        logger.info("Successfully running MiniRPG!")
        pluginInit()
        commandRegister()
        listenerRegister()
    }

    fun onDisable() {

    }

    private fun listenerRegister() {
        plugin.server.pluginManager.registerEvents(CommonListener(), plugin)
    }
    private fun commandRegister() {
        commandManager.registerCommand(MRCommand())
    }
    private fun pluginInit() {
        //  初始化数据文件夹
        val dataFolder = plugin.dataFolder
        if (!dataFolder.exists()) { dataFolder.mkdir() }

        //  初始化数据库
        database = DataBase()

    }


}
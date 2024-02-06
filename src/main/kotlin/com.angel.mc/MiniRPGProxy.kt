package com.angel.mc

import com.angel.mc.configuration.Configuration
import com.angel.mc.database.DataBase
import com.angel.mc.database.DatabaseCfg
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.ibatis.io.Resources
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import kotlin.math.log

class MiniRPGProxy() {
    lateinit var plugin: JavaPlugin
    private lateinit var logger: Logger
    private lateinit var database: DataBase

    fun bind(plugin: JavaPlugin) {
        this.plugin = plugin
    }

    fun onEnable() {
        logger = plugin.server.logger
        logger.info("Successfully running MiniRPG!")
        pluginInit()
    }

    fun onDisable() {

    }

    private fun pluginInit() {
        //  初始化数据文件夹
        val dataFolder = plugin.dataFolder
        if (!dataFolder.exists()) { dataFolder.mkdir() }

        //  初始化数据库
        database = DataBase()

    }


}
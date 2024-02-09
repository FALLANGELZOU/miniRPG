package com.angel.mc.database

import com.alibaba.druid.pool.DruidDataSource
import com.angel.mc.KeySet.DATABASE.DATABASE_CONFIG
import com.angel.mc.MiniRPG
import com.angel.mc.configuration.Configuration
import com.angel.mc.configuration.newFile
import com.angel.mc.dao.MoneyMapper
import com.angel.mc.dao.PlayerMapper
import com.mybatisflex.kotlin.scope.runFlex
import com.zaxxer.hikari.HikariConfig
import org.apache.ibatis.io.Resources
import org.apache.ibatis.logging.stdout.StdOutImpl
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.sql.SQLException
import javax.sql.DataSource


class DataBase(
    val framework:  DataBaseFrameworkType = DataBaseFrameworkType.MYBATIS_FLEX
) {
    init {
        val dataSource = databaseCfgInit()
        when(framework) {
            DataBaseFrameworkType.MYBATIS_FLEX -> flexInit(dataSource)
            else -> throw Error("unknown framework")
        }
    }
    enum class DataBaseFrameworkType {
        MYBATIS_FLEX
    }

    enum class DataBaseType(val type: String, val driver: String) {
        SQLITE("sqlite", "org.sqlite.JDBC"),
        MYSQL("mysql", "com.mysql.jdbc.Driver"),
//        MONGODB("mongodb")
        ;
        companion object {
            fun get(name: String?): DataBaseType {
                return when(name) {
                    "sqlite" -> DataBaseType.SQLITE
                    "mysql" -> DataBaseType.MYSQL
//                    "mongodb" -> DataBaseType.MONGODB
                    else -> throw Error("unknown dataset!")
                }
            }
        }

    }

    private fun databaseCfgInit(): DataSource {
        //  判断是否已经存在配置
        val outDatabaseCfgFile = newFile(MiniRPG.ktProxy.plugin.dataFolder, DATABASE_CONFIG, false)
        val cfg = if (!outDatabaseCfgFile.exists()) {
            //  读取默认数据库设置
            val cfg = Resources.getResourceAsStream(DATABASE_CONFIG)
            val databaseCfg = Configuration.YAML_MANAGER.load(cfg, DatabaseCfg::class.java)
            Configuration.YAML_MANAGER.write2File(outDatabaseCfgFile, databaseCfg)
            databaseCfg
        } else {
            //  读取用户自定义设置
            Configuration.YAML_MANAGER.load(outDatabaseCfgFile, DatabaseCfg::class.java)
        }


        //数据源配置
        val datasetType = DataBaseType.get(cfg.dataset)
        val url = if (datasetType == DataBaseType.SQLITE) {
            "jdbc:sqlite:${MiniRPG.ktProxy.plugin.dataFolder.absolutePath}${File.separator}${cfg.url}"
        } else {
            cfg.url
        }

//        val dsConfig = HikariConfig().also {
////            it.driverClassName = datasetType.driver
//            it.jdbcUrl = url
//
//        }

        val dataSource =  DruidDataSource()
        dataSource.driverClassName = datasetType.driver
        dataSource.url = url
        cfg.username?.let { dataSource.username = it }
        cfg.password?.let { dataSource.password = it }
        cfg.initialSize?.let { dataSource.initialSize = it }
        cfg.maxActive?.let { dataSource.maxActive = it }
        cfg.maxWait?.let { dataSource.maxWait = it }

        executeInitScript(dataSource, "init.sql");
        return dataSource

    }

    private fun flexInit(dataSource: DataSource) {
        MiniRPG.ktProxy.bootstrap = runFlex {
            +dataSource
            +PlayerMapper::class
            +MoneyMapper::class
            logImpl = StdOutImpl::class

        }

    }

    @Throws(SQLException::class)
    private fun executeInitScript(dataSource: DataSource, scriptPath: String) {
        dataSource.connection.use { connection ->
            val scriptStream = Resources.getResourceAsStream(scriptPath)
            val reader = InputStreamReader(scriptStream)
            val buffer = BufferedReader(reader)
            val statement = connection.createStatement()

            var line: String?
            val sqlStatements = StringBuilder()

            while (buffer.readLine().also { line = it } != null) {
                val trimmedLine = line!!.trim()
                if (trimmedLine.isNotEmpty() && !trimmedLine.startsWith("--")) {
                    sqlStatements.append(line).append(" ")
                    if (trimmedLine.endsWith(";")) {
                        statement.execute(sqlStatements.toString())
                        sqlStatements.setLength(0) // 清空 StringBuilder
                    }
                }
            }

            statement.close()
        }
    }


}
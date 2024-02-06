package com.angel.mc.database

class DatabaseCfg(
    val dataset: String?,
    val url: String?,
    val username: String?,
    val password: String?,
    val initialSize: Int?,
    val maxActive: Int?,
    val maxWait: Long?
)
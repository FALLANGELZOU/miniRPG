package com.angel.mc.configuration

import com.angel.mc.database.DatabaseCfg
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.InputStream

object Configuration {

    class ConfigurationManager(configType: String) {
        private lateinit var mapper: ObjectMapper
        init {
            mapper = when(configType) {
                "yaml" -> getMapper(YAMLFactory())
                else -> throw Error("unknown config type!")
            }
        }

        fun <T> load(src: InputStream, valueType:Class<T>): T {
            return mapper.readValue(src, valueType)
        }

        fun <T> load(src: File, valueType:Class<T>): T {
            return mapper.readValue(src, valueType)
        }

        fun <T> write2File(filePath: String, data: T) {
            mapper.writeValue(File(filePath), data)
        }

        fun <T> write2File(file: File, data: T) {
            mapper.writeValue(file, data)
        }


        private fun getMapper(factory: JsonFactory): ObjectMapper {
            return ObjectMapper(factory).registerModule(KotlinModule())
        }
    }


    val YAML_MANAGER by lazy { ConfigurationManager("yaml") }



}
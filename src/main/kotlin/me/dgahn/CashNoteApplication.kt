package me.dgahn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
class CashNoteApplication

fun main() {
    runApplication<CashNoteApplication>()
}

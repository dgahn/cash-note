package me.dgahn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class CashNoteApplication

fun main() {
    runApplication<CashNoteApplication>()
}

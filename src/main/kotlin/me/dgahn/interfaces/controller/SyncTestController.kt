package me.dgahn.interfaces.controller

import me.dgahn.application.service.InitCardTransactionHistorySyncService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class SyncTestController(
    private val initSynService: InitCardTransactionHistorySyncService,
) {
    @GetMapping("/sync-init")
    fun syncInit() {
        initSynService.sync()
    }

    @GetMapping("/sync-daily")
    fun syncDaily() {
        initSynService.sync()
    }
}

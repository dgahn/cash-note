package me.dgahn.interfaces.controller

import me.dgahn.application.service.DailyCardTransactionHistorySyncService
import me.dgahn.application.service.InitCardTransactionHistorySyncService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class SyncTestController(
    private val initSynService: InitCardTransactionHistorySyncService,
    private val dailySynService: DailyCardTransactionHistorySyncService,
) {
    @GetMapping("/sync-init")
    fun syncInit() {
        initSynService.sync()
    }

    @GetMapping("/sync-daily")
    fun syncDaily() {
        dailySynService.sync()
    }
}

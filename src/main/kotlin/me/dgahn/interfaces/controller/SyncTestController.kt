package me.dgahn.interfaces.controller

import me.dgahn.application.service.DailyCardTransactionHistorySyncService
import me.dgahn.application.service.InitCardTransactionHistorySyncService
import me.dgahn.infrastructure.queue.event.CardTransactionHistoryPayload
import me.dgahn.infrastructure.queue.producer.CardHistoryEventProducer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class SyncTestController(
    private val initSynService: InitCardTransactionHistorySyncService,
    private val dailySynService: DailyCardTransactionHistorySyncService,
    private val producer: CardHistoryEventProducer,
) {
    @GetMapping("/sync-init")
    fun syncInit() {
        initSynService.sync()
    }

    @GetMapping("/sync-daily")
    fun syncDaily() {
        dailySynService.sync()
    }

    @GetMapping("/sync-community")
    fun syncCommunity() {
        producer.send((1..100).map { CardTransactionHistoryPayload() })
    }
}

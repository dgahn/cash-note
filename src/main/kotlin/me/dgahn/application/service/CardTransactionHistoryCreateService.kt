package me.dgahn.application.service

import me.dgahn.domain.service.CardTransactionHistorySaver
import me.dgahn.domain.service.CommunitySyncInfoSearcher
import me.dgahn.infrastructure.queue.event.CardTransactionHistoryPayload
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardTransactionHistoryCreateService(
    private val cardTransactionHistorySaver: CardTransactionHistorySaver,
    private val communitySyncInfoSearcher: CommunitySyncInfoSearcher
) {
    @Transactional
    fun create(payloads: List<CardTransactionHistoryPayload>) {
        payloads.forEach { payload ->
            val syncInfo = communitySyncInfoSearcher.search(payload.registrationNumber)
            val history = payload.toDomain()
            cardTransactionHistorySaver.save(syncInfo, history)
        }
    }
}

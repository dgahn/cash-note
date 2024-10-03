package me.dgahn.domain.service

import me.dgahn.domain.model.CardTransactionHistory
import me.dgahn.domain.model.CommunitySyncInfo
import me.dgahn.infrastructure.database.entity.toEntity
import me.dgahn.infrastructure.database.repository.CardTransactionHistoryRepository
import org.springframework.stereotype.Service

@Service
class CardTransactionHistorySaver(
    private val cardTransactionHistoryRepository: CardTransactionHistoryRepository,
) {
    fun save(syncInfo: CommunitySyncInfo, cardTransactionHistory: CardTransactionHistory): CardTransactionHistory {
        return cardTransactionHistoryRepository.save(cardTransactionHistory.toEntity(syncInfo.toEntity())).toDomain()
    }
}

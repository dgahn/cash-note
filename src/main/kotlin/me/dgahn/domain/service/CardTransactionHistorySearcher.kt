package me.dgahn.domain.service

import me.dgahn.domain.model.CardTransactionHistory
import me.dgahn.domain.model.CommunitySyncInfo
import me.dgahn.domain.model.Provider
import me.dgahn.infrastructure.database.entity.toEntity
import me.dgahn.infrastructure.database.repository.CardTransactionHistoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CardTransactionHistorySearcher(
    private val cardTransactionHistoryRepository: CardTransactionHistoryRepository,
) {
    fun search(
        syncInfo: CommunitySyncInfo,
        startAt: LocalDate,
        endAt: LocalDate,
        provider: Provider,
    ): List<CardTransactionHistory> {
        return cardTransactionHistoryRepository
            .findAllByCommunitySyncInfoEntityAndTransactionDateBetweenAndProvider(
                communitySyncInfoEntity = syncInfo.toEntity(),
                startAt = startAt,
                endAt = endAt,
                provider = provider,
            ).map { it.toDomain() }
    }
}

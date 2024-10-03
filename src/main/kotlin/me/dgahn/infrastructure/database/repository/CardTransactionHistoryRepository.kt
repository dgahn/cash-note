package me.dgahn.infrastructure.database.repository

import me.dgahn.infrastructure.database.entity.CardTransactionHistoryEntity
import me.dgahn.infrastructure.database.entity.CommunitySyncInfoEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface CardTransactionHistoryRepository : JpaRepository<CardTransactionHistoryEntity, String> {
    fun findAllByCommunitySyncInfoEntityAndTransactionDateBetween(
        communitySyncInfoEntity: CommunitySyncInfoEntity,
        startAt: LocalDate,
        endAt: LocalDate,
    ): List<CardTransactionHistoryEntity>
}

package me.dgahn.application.service

import me.dgahn.domain.model.SyncStatus
import me.dgahn.domain.service.CardTransactionHistorySearcher
import me.dgahn.domain.service.CommunitySyncInfoSearcher
import me.dgahn.infrastructure.client.http.client.CommunityClient
import me.dgahn.infrastructure.client.http.dto.toRegisterRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class InitCardTransactionHistorySyncService(
    private val communitySyncInfoSearcher: CommunitySyncInfoSearcher,
    private val cardTransactionHistorySearcher: CardTransactionHistorySearcher,
    private val communityClient: CommunityClient,
) {
    @Transactional
    fun sync() {
        val endAt = LocalDate.now()
        val startAt = endAt.minusMonths(INITIAL_SYNC_PERIOD)
        val syncInfos = communitySyncInfoSearcher.search(SyncStatus.READY)
        syncInfos.forEach { syncInfo ->
            val histories = cardTransactionHistorySearcher.search(
                syncInfo = syncInfo,
                startAt = startAt,
                endAt = endAt,
            )
            communityClient.registerDataCard(histories.map { it.toRegisterRequestDto() })
        }
    }

    companion object {
        private const val INITIAL_SYNC_PERIOD = 6L
    }
}

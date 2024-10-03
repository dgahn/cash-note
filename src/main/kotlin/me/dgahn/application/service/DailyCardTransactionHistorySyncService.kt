package me.dgahn.application.service

import me.dgahn.domain.model.Provider
import me.dgahn.domain.model.SyncStatus
import me.dgahn.domain.service.CardTransactionHistorySearcher
import me.dgahn.domain.service.CommunitySyncInfoSearcher
import me.dgahn.infrastructure.client.http.client.CommunityClient
import me.dgahn.infrastructure.client.http.dto.toRegisterRequestDto
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

@Service
class DailyCardTransactionHistorySyncService(
    private val communitySyncInfoSearcher: CommunitySyncInfoSearcher,
    private val cardTransactionHistorySearcher: CardTransactionHistorySearcher,
    private val communityClient: CommunityClient,
) {
    fun sync() {
        val endAt = LocalDate.now()
        val startAt = endAt.minusDays(DAILY_SYNC_PERIOD)
        val syncInfos = communitySyncInfoSearcher.search(listOf(SyncStatus.INITIAL_SYNC, SyncStatus.DAILY_SYNC))
        logger.info { "일일 데이터 전송하는 사업자 번호는 [${syncInfos.joinToString { it.registrationNumber.value }}] 입니다." }
        syncInfos.forEach { syncInfo ->
            val histories = cardTransactionHistorySearcher.search(
                syncInfo = syncInfo,
                startAt = startAt,
                endAt = endAt,
                provider = Provider.CASH_NOTE,
            )
            syncInfo.dailySynced()
            communityClient.registerDataCard(histories.map { it.toRegisterRequestDto() })
        }
    }

    companion object {
        const val DAILY_SYNC_PERIOD = 1L
    }
}

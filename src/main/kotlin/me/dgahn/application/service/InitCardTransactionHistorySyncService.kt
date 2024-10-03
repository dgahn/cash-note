package me.dgahn.application.service

import me.dgahn.domain.model.Provider
import me.dgahn.domain.model.SyncStatus
import me.dgahn.domain.service.CardTransactionHistorySearcher
import me.dgahn.domain.service.CommunitySyncInfoSearcher
import me.dgahn.infrastructure.client.http.client.CommunityClient
import me.dgahn.infrastructure.client.http.dto.toRegisterRequestDto
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

private val logger = KotlinLogging.logger { }

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
        logger.info { "최초 데이터 전송하는 사업자 번호는 [${syncInfos.joinToString { it.registrationNumber.value }}] 입니다." }
        syncInfos.forEach { syncInfo ->
            val histories = cardTransactionHistorySearcher.search(
                syncInfo = syncInfo,
                startAt = startAt,
                endAt = endAt,
                provider = Provider.CASH_NOTE,
            )
            syncInfo.initSynced()
            communityClient.registerDataCard(histories.map { it.toRegisterRequestDto() })
        }
    }

    companion object {
        private const val INITIAL_SYNC_PERIOD = 6L
    }
}

package me.dgahn.util

import me.dgahn.domain.model.Provider
import me.dgahn.domain.model.SyncStatus
import me.dgahn.domain.model.TransactionType
import me.dgahn.infrastructure.database.entity.CardTransactionHistoryEntity
import me.dgahn.infrastructure.database.entity.CommunitySyncInfoEntity
import me.dgahn.infrastructure.database.repository.CardTransactionHistoryRepository
import me.dgahn.infrastructure.database.repository.CommunitySyncInfoRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

@Component
class SampleInsertRunner(
    private val communitySyncInfoRepository: CommunitySyncInfoRepository,
    private val cardTransactionHistoryRepository: CardTransactionHistoryRepository,
) : CommandLineRunner {
    private val cardCompanies = listOf("농협NH카드", "하나카드", "신한카드", "비씨카드", "KB카드")

    @Transactional
    override fun run(vararg args: String?) {
        listOf(notSyncedInfos(), readyInfo(), syncedInfo(), dailyInfo()).map { syncInfo ->
            cardTransactionHistoryRepository.saveAll(initNotIncludeHistories(syncInfo))
            cardTransactionHistoryRepository.saveAll(initIncludeHistories(syncInfo))
            cardTransactionHistoryRepository.saveAll(yesterdayHistories(syncInfo))
        }
    }

    // 약관 동의 안한 정보
    private fun notSyncedInfos(): CommunitySyncInfoEntity = communitySyncInfoRepository.save(
        CommunitySyncInfoEntity(
            registrationNumber = "111-11-11111",
            syncStatus = SyncStatus.NOT_SYNCED,
        ),
    )

    // 약관 동의 한 정보
    private fun readyInfo(): CommunitySyncInfoEntity = communitySyncInfoRepository.save(
        CommunitySyncInfoEntity(
            registrationNumber = "222-22-22222",
            syncStatus = SyncStatus.READY,
        ),
    )

    // 최초 데이터 전송 완료한 정보
    private fun syncedInfo(): CommunitySyncInfoEntity = communitySyncInfoRepository.save(
        CommunitySyncInfoEntity(
            registrationNumber = "333-33-33333",
            syncStatus = SyncStatus.INITIAL_SYNC,
        ),
    )

    // 이미 데일리 하고 있는 정보
    private fun dailyInfo(): CommunitySyncInfoEntity = communitySyncInfoRepository.save(
        CommunitySyncInfoEntity(
            registrationNumber = "444-44-44444",
            syncStatus = SyncStatus.DAILY_SYNC,
        ),
    )

    private fun initIncludeHistories(syncInfo: CommunitySyncInfoEntity): List<CardTransactionHistoryEntity> =
        (1..1000).map {
            // 6개월 ~ 2일전까지
            val transactionDate = LocalDate.now().minusMonths(Random.nextInt(0, 6).toLong()).minusDays(2)
            CardTransactionHistoryEntity(
                approvalNumber = approvalNumber(),
                transactionType = TransactionType.APPROVAL,
                transactionDate = transactionDate,
                transactionTime = transactionTime(),
                cardCompany = randomCardCompany(),
                affiliatedCardCompany = "",
                cardNumber = cardNumber(),
                approvalAmount = 12000,
                installmentPeriod = "일시불",
                provider = Provider.CASH_NOTE,
                communitySyncInfoEntity = syncInfo,
            )
        }

    private fun initNotIncludeHistories(syncInfo: CommunitySyncInfoEntity): List<CardTransactionHistoryEntity> =
        (1..1000).map {
            // (6개월 + 1일) 이전 데이터
            val transactionDate = LocalDate.now().minusMonths(Random.nextInt(6, 10).toLong()).minusDays(1)
            CardTransactionHistoryEntity(
                approvalNumber = approvalNumber(),
                transactionType = TransactionType.APPROVAL,
                transactionDate = transactionDate,
                transactionTime = transactionTime(),
                cardCompany = randomCardCompany(),
                affiliatedCardCompany = "",
                cardNumber = cardNumber(),
                approvalAmount = 12000,
                installmentPeriod = "일시불",
                provider = Provider.CASH_NOTE,
                communitySyncInfoEntity = syncInfo,
            )
        }

    private fun yesterdayHistories(syncInfo: CommunitySyncInfoEntity): List<CardTransactionHistoryEntity> =
        (1..100).map {
            val transactionDate = LocalDate.now().minusDays(1)
            CardTransactionHistoryEntity(
                approvalNumber = approvalNumber(),
                transactionType = TransactionType.APPROVAL,
                transactionDate = transactionDate,
                transactionTime = transactionTime(),
                cardCompany = randomCardCompany(),
                affiliatedCardCompany = "",
                cardNumber = cardNumber(),
                approvalAmount = 12000,
                installmentPeriod = "일시불",
                provider = Provider.CASH_NOTE,
                communitySyncInfoEntity = syncInfo,
            )
        }

    private fun cardNumber(): String =
        "${Random.nextInt(1000, 10000)}-${Random.nextInt(1000, 10000)}-****-****"

    private fun randomCardCompany(): String = cardCompanies.shuffled().first()

    private fun transactionTime(): LocalTime =
        LocalTime.of(Random.nextInt(0, 13), Random.nextInt(0, 25))

    private fun approvalNumber(): String = Random.nextInt(10000000, 100000000).toString()
}

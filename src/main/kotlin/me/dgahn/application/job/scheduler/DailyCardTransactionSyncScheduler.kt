package me.dgahn.application.job.scheduler

import me.dgahn.application.service.DailyCardTransactionHistorySyncService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class DailyCardTransactionSyncScheduler(
    @Value("\${schedule.card-transaction-history-sync.use}") private val useSchedule: Boolean,
    private val syncService: DailyCardTransactionHistorySyncService,
) {
    @Scheduled(cron = "\${schedule.card-transaction-history-sync.cron}")
    fun sync() {
        if (!useSchedule) {
            logger.info { "일일 매출 싱크 스케줄러가 꺼져있습니다." }
            return
        }

        syncService.sync()
    }
}

package me.dgahn.interfaces.job.scheduler

import me.dgahn.application.service.InitCardTransactionHistorySyncService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class InitCardTransactionSyncScheduler(
    @Value("\${schedule.card-transaction-history-sync.use}") private val useSchedule: Boolean,
    private val syncService: InitCardTransactionHistorySyncService,
) {
    @Scheduled(cron = "\${schedule.card-transaction-history-sync.cron}")
    fun sync() {
        if (!useSchedule) {
            logger.info { "최초 매출 싱크 스케줄러가 꺼져있습니다." }
            return
        }

        // ToDo 청크 처리 필요
        syncService.sync()
    }
}

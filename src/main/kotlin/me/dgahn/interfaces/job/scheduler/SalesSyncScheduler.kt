package me.dgahn.interfaces.job.scheduler

import me.dgahn.application.service.SalesSyncService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class SalesSyncScheduler(
    @Value("\${schedule.sales-sync.use}") private val useSchedule: Boolean,
    private val salesSyncService: SalesSyncService
) {
    @Scheduled(cron = "\${schedule.sales-sync.cron}")
    fun sync() {
        if (!useSchedule) {
            logger.info { "매출 싱크 스케줄러가 꺼져있습니다." }
            return
        }

        salesSyncService.sync()
    }
}

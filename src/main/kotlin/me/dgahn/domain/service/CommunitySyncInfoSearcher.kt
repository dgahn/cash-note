package me.dgahn.domain.service

import me.dgahn.domain.model.CommunitySyncInfo
import me.dgahn.domain.model.SyncStatus
import me.dgahn.infrastructure.database.repository.CommunitySyncInfoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommunitySyncInfoSearcher(
    private val communitySyncInfoRepository: CommunitySyncInfoRepository,
) {
    fun search(syncStatus: SyncStatus): List<CommunitySyncInfo> {
        return communitySyncInfoRepository
            .findAllBySyncStatus(syncStatus)
            .map { it.toDomain() }
    }

    fun search(syncStatuses: List<SyncStatus>): List<CommunitySyncInfo> {
        return communitySyncInfoRepository
            .findAllBySyncStatusIn(syncStatuses)
            .map { it.toDomain() }
    }

    fun search(registrationNumber: String): CommunitySyncInfo {
        return communitySyncInfoRepository
            .findByIdOrNull(registrationNumber)
            ?.toDomain()
            ?: TODO("못 찾을 경우에 대한 예외 발생")
    }
}

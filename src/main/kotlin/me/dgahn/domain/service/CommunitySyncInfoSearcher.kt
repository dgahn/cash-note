package me.dgahn.domain.service

import me.dgahn.domain.model.CommunitySyncInfo
import me.dgahn.domain.model.SyncStatus
import me.dgahn.infrastructure.database.repository.CommunitySyncInfoRepository
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
}

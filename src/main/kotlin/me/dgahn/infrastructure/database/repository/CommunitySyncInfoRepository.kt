package me.dgahn.infrastructure.database.repository

import me.dgahn.domain.model.SyncStatus
import me.dgahn.infrastructure.database.entity.CommunitySyncInfoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommunitySyncInfoRepository : JpaRepository<CommunitySyncInfoEntity, String> {
    fun findAllBySyncStatus(syncStatus: SyncStatus): List<CommunitySyncInfoEntity>
}

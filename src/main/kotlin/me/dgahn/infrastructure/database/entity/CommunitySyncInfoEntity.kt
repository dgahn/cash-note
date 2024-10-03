package me.dgahn.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.dgahn.domain.model.CommunitySyncInfo
import me.dgahn.domain.model.RegistrationNumber
import me.dgahn.domain.model.SyncStatus
import java.time.LocalDateTime

@Entity
@Table(name = "community_sync_info")
class CommunitySyncInfoEntity(
    @Id
    @Column(name = "registration_number", updatable = false, nullable = false)
    val registrationNumber: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status")
    val syncStatus: SyncStatus,
    @Column(name = "last_synced_at")
    val lastSyncedAt: LocalDateTime?,
) : BaseEntity() {
    fun toDomain(): CommunitySyncInfo {
        return CommunitySyncInfo(
            registrationNumber = RegistrationNumber.from(this.registrationNumber),
            syncStatus = this.syncStatus,
            lastSyncedAt = this.lastSyncedAt,
        )
    }
}

fun CommunitySyncInfo.toEntity(): CommunitySyncInfoEntity {
    return CommunitySyncInfoEntity(
        registrationNumber = this.registrationNumber.value,
        syncStatus = this.syncStatus,
        lastSyncedAt = this.lastSyncedAt,
    )
}

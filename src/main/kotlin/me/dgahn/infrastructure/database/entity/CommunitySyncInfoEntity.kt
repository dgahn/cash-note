package me.dgahn.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import me.dgahn.domain.model.CommunitySyncInfo
import me.dgahn.domain.model.RegistrationNumber
import me.dgahn.domain.model.SyncStatus

@Entity
@Table(
    name = "community_sync_info",
    indexes = [
        Index(name = "idx_created_at", columnList = "created_at"),
    ]
)
class CommunitySyncInfoEntity(
    @Id
    @Column(name = "registration_number", updatable = false, nullable = false)
    val registrationNumber: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status")
    val syncStatus: SyncStatus,
) : BaseEntity() {
    fun toDomain(): CommunitySyncInfo {
        return CommunitySyncInfo(
            registrationNumber = RegistrationNumber.from(this.registrationNumber),
            syncStatus = this.syncStatus,
        )
    }
}

fun CommunitySyncInfo.toEntity(): CommunitySyncInfoEntity {
    return CommunitySyncInfoEntity(
        registrationNumber = this.registrationNumber.value,
        syncStatus = this.syncStatus,
    )
}

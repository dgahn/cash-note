package me.dgahn.domain.model

import java.time.LocalDateTime

data class CommunitySyncInfo(
    val registrationNumber: RegistrationNumber,
    val syncStatus: SyncStatus,
    val lastSyncedAt: LocalDateTime?,
)

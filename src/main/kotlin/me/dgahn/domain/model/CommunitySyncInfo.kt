package me.dgahn.domain.model

data class CommunitySyncInfo(
    val registrationNumber: RegistrationNumber,
    var syncStatus: SyncStatus,
) {
    fun initSynced() {
        check(syncStatus == SyncStatus.READY) { "INITIAIL_SYNC로 변경할 수 없습니다. ($syncStatus" }
        this.syncStatus = SyncStatus.INITIAL_SYNC
    }

    fun dailySynced() {
        check(syncStatus == SyncStatus.INITIAL_SYNC || syncStatus == SyncStatus.DAILY_SYNC) {
            "DAILY_SYNC로 변경할 수 없습니다. ($syncStatus"
        }
        this.syncStatus = SyncStatus.DAILY_SYNC
    }

    companion object {
        fun of(term: Term): CommunitySyncInfo {
            return CommunitySyncInfo(
                registrationNumber = term.registrationNumber,
                syncStatus = SyncStatus.NOT_SYNCED,
            )
        }
    }
}

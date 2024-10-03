package me.dgahn.domain.model

/**
 * 공동체와의 동기화 상태
 *
 * 추후 PENDING(보류), DISCONNECTED(중단), FAILED(실패)등의 상태가 생길 수 있으므로 enum으로 선언
 */
enum class SyncStatus {
    NOT_SYNCED,
    READY,
    INITIAL_SYNC,
    DAILY_SYNC,
}

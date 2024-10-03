package me.dgahn.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CommunitySyncInfoTest {
    @Test
    fun `초기 동기화 상태 변경 - READY일 때 INITIAL_SYNC로 변경`() {
        val communitySyncInfo = CommunitySyncInfo(
            registrationNumber = RegistrationNumber("123-45-67890"),
            syncStatus = SyncStatus.READY,
        )

        communitySyncInfo.initSynced()

        communitySyncInfo.syncStatus shouldBe SyncStatus.INITIAL_SYNC
    }

    @Test
    fun `초기 동기화 상태 변경 실패 - READY 상태가 아니면 INITIAL_SYNC로 변경 불가`() {
        val communitySyncInfo = CommunitySyncInfo(
            registrationNumber = RegistrationNumber("123-45-67890"),
            syncStatus = SyncStatus.DAILY_SYNC,
        )

        val exception = shouldThrow<IllegalStateException> {
            communitySyncInfo.initSynced()
        }

        exception.message shouldBe "INITIAIL_SYNC로 변경할 수 없습니다. (DAILY_SYNC)"
    }

    @Test
    fun `일일 동기화 상태 변경 - INITIAL_SYNC 또는 DAILY_SYNC 상태에서 DAILY_SYNC로 변경`() {
        val communitySyncInfo = CommunitySyncInfo(
            registrationNumber = RegistrationNumber("123-45-67890"),
            syncStatus = SyncStatus.INITIAL_SYNC,
        )

        communitySyncInfo.dailySynced()

        communitySyncInfo.syncStatus shouldBe SyncStatus.DAILY_SYNC
    }

    @Test
    fun `일일 동기화 상태 변경 실패 - INITIAL_SYNC 또는 DAILY_SYNC 상태가 아니면 DAILY_SYNC로 변경 불가`() {
        val communitySyncInfo = CommunitySyncInfo(
            registrationNumber = RegistrationNumber("123-45-67890"),
            syncStatus = SyncStatus.READY,
        )

        val exception = shouldThrow<IllegalStateException> {
            communitySyncInfo.dailySynced()
        }

        exception.message shouldBe "DAILY_SYNC로 변경할 수 없습니다. (READY)"
    }
}

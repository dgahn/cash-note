package me.dgahn.infrastructure.queue.event

import com.fasterxml.jackson.annotation.JsonFormat
import me.dgahn.domain.model.CardTransactionHistory
import me.dgahn.domain.model.Provider
import me.dgahn.domain.model.TransactionType
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

data class CardTransactionHistoryPayload(
    val registrationNumber: String = "444-44-44444",
    val approvalNumber: String = Random.nextInt(10000000, 100000000).toString(),
    val transactionType: TransactionType = TransactionType.APPROVAL,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val transactionDate: LocalDate = LocalDate.now().minusDays(1),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    val transactionTime: LocalTime = LocalTime.now().minusHours(1),
    val cardCompany: String = "비씨카드",
    val affiliatedCardCompany: String = "",
    val cardNumber: String = "${Random.nextInt(1000, 10000)}-${Random.nextInt(1000, 10000)}-****-****",
    val approvalAmount: Int = 12000,
    val installmentPeriod: String = "일시불",
    val provider: Provider = Provider.COMMUNITY,
) {
    fun toDomain(): CardTransactionHistory {
        return CardTransactionHistory(
            approvalNumber = this.approvalNumber,
            transactionType = this.transactionType,
            transactionDate = this.transactionDate,
            transactionTime = this.transactionTime,
            cardCompany = this.cardCompany,
            affiliatedCardCompany = this.affiliatedCardCompany,
            cardNumber = this.cardNumber,
            approvalAmount = this.approvalAmount,
            installmentPeriod = this.installmentPeriod,
            provider = this.provider,
        )
    }
}

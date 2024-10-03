package me.dgahn.infrastructure.client.http.dto

import me.dgahn.domain.model.CardTransactionHistory
import me.dgahn.domain.model.Provider
import me.dgahn.domain.model.TransactionType
import java.time.LocalDate
import java.time.LocalTime

data class RegisterDataCardRequestDto(
    val approvalNumber: String,
    val transactionType: TransactionType,
    val transactionDate: LocalDate,
    val transactionTime: LocalTime,
    val cardCompany: String,
    val affiliatedCardCompany: String,
    val cardNumber: String,
    val approvalAmount: Int,
    val installmentPeriod: String,
    val provider: Provider,
)

fun CardTransactionHistory.toRegisterRequestDto(): RegisterDataCardRequestDto {
    return RegisterDataCardRequestDto(
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

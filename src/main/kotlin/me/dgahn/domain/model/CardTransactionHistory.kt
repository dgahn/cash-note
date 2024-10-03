package me.dgahn.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class CardTransactionHistory(
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

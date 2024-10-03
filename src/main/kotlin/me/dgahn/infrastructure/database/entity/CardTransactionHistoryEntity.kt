package me.dgahn.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import me.dgahn.domain.model.CardTransactionHistory
import me.dgahn.domain.model.Provider
import me.dgahn.domain.model.TransactionType
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "card_transaction_history")
class CardTransactionHistoryEntity(
    @Id
    @Column(name = "approval_number", updatable = false, nullable = false)
    val approvalNumber: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    val transactionType: TransactionType,
    @Column(name = "transaction_date")
    val transactionDate: LocalDate,
    @Column(name = "transaction_time")
    val transactionTime: LocalTime,
    @Column(name = "card_company")
    val cardCompany: String,
    @Column(name = "affiliated_card_company")
    val affiliatedCardCompany: String,
    @Column(name = "card_number")
    val cardNumber: String,
    @Column(name = "approval_amount")
    val approvalAmount: Int,
    @Column(name = "installment_period")
    val installmentPeriod: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    val provider: Provider,
    @ManyToOne
    @JoinColumn(name = "registration_number")
    val communitySyncInfoEntity: CommunitySyncInfoEntity,
) : BaseEntity() {
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

fun CardTransactionHistory.toEntity(syncInfoEntity: CommunitySyncInfoEntity): CardTransactionHistoryEntity {
    return CardTransactionHistoryEntity(
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
        communitySyncInfoEntity = syncInfoEntity,
    )
}

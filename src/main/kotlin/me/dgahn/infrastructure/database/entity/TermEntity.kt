package me.dgahn.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.dgahn.domain.model.AgreeStatus
import me.dgahn.domain.model.Term

@Entity
@Table(name = "term")
class TermEntity(
    @Id
    @Column(name = "registration_number", updatable = false, nullable = false)
    val registrationNumber: String,
    @Enumerated(EnumType.STRING)
    val agreeStatus: AgreeStatus,
) : BaseEntity() {
    fun toDomain(): Term {
        return Term(
            registrationNumber = registrationNumber,
            agreeStatus = agreeStatus,
        )
    }
}

fun Term.toEntity(): TermEntity = TermEntity(registrationNumber = registrationNumber, agreeStatus = agreeStatus)

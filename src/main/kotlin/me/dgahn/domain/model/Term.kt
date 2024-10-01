package me.dgahn.domain.model

data class Term(
    val registrationNumber: String,
    val agreeStatus: AgreeStatus,
) {
    companion object {
        fun create(registrationNumber: String, agreedType: Boolean): Term {
            return Term(
                registrationNumber = registrationNumber,
                agreeStatus = AgreeStatus.of(agreedType),
            )
        }
    }
}

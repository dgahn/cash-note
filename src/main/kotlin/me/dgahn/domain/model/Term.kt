package me.dgahn.domain.model

data class Term(
    val registrationNumber: RegistrationNumber,
    val agreeStatus: AgreeStatus,
) {
    companion object {
        fun create(registrationNumber: String, agreedType: Boolean): Term {
            return Term(
                registrationNumber = RegistrationNumber.from(registrationNumber),
                agreeStatus = AgreeStatus.of(agreedType),
            )
        }
    }
}

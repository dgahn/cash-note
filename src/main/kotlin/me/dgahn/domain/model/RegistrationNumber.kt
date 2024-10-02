package me.dgahn.domain.model

@JvmInline
value class RegistrationNumber(
    val value: String,
) {
    init {
        require(isValidRegistrationNumber(value)) { "Invalid business registration number format" }
    }

    companion object {
        private val REGISTRATION_NUMBER_REGEX = Regex("^\\d{3}-\\d{2}-\\d{5}$")

        private fun isValidRegistrationNumber(value: String): Boolean {
            return REGISTRATION_NUMBER_REGEX.matches(value)
        }

        fun from(value: String): RegistrationNumber {
            return RegistrationNumber(value)
        }
    }
}

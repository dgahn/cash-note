package me.dgahn.interfaces.controller

import me.dgahn.interfaces.dto.TermAgreementRequestDto

object TermFixture {
    val TERM_AGREEMET_REQUEST = TermAgreementRequestDto(
        registrationNumber = "123-45-67890",
        agreedType = true,
    )
}

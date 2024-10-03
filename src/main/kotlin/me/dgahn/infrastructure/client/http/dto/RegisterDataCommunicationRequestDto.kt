package me.dgahn.infrastructure.client.http.dto

import me.dgahn.domain.model.Term

data class RegisterDataCommunicationRequestDto(
    val registrationNumber: String,
    val agreedType: Boolean,
)

fun Term.toRegisterRequestDto() = RegisterDataCommunicationRequestDto(
    registrationNumber = this.registrationNumber.value,
    agreedType = this.agreeStatus.agreedType,
)

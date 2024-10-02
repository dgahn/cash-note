package me.dgahn.infrastructure.client.http.dto

import me.dgahn.domain.model.Term

data class RegisterRequestDto(
    val registrationNumber: String,
    val agreedType: Boolean,
)

fun Term.toRegisterRequestDto() = RegisterRequestDto(
    registrationNumber = this.registrationNumber.value,
    agreedType = this.agreeStatus.agreedType,
)

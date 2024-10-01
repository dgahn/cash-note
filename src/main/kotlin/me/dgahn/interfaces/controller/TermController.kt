package me.dgahn.interfaces.controller

import me.dgahn.application.service.TermCreator
import me.dgahn.interfaces.dto.TermAgreementRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class TermController(
    private val termCreator: TermCreator,
) {
    @PostMapping("/api/v1/terms/agreement")
    fun agree(
        @RequestBody request: TermAgreementRequestDto,
    ): ResponseEntity<Unit> {
        termCreator.createTerm(request.registrationNumber, request.agreedType)
        return ResponseEntity.ok().build()
    }
}

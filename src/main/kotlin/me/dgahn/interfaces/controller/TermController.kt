package me.dgahn.interfaces.controller

import me.dgahn.application.service.TermCreateService
import me.dgahn.interfaces.dto.TermAgreementRequestDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class TermController(
    private val termCreateService: TermCreateService,
) {
    @PostMapping("/api/v1/terms/agreement")
    fun agree(
        @RequestBody request: TermAgreementRequestDto,
    ) {
        termCreateService.createTerm(request.registrationNumber, request.agreedType)
    }
}

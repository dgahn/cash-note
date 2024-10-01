package me.dgahn.interfaces.controller

import me.dgahn.interfaces.dto.QuickConnectConfirmResultDto
import me.dgahn.interfaces.dto.TermAgreementRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class CommunityMockController {
    @GetMapping("/has-business")
    fun check(
        @RequestParam("registrationNumber") registrationNumber: String,
    ): ResponseEntity<QuickConnectConfirmResultDto> {
        return ResponseEntity.ok(QuickConnectConfirmResultDto(true))
    }

    @PostMapping("/register-data-communication")
    fun register(
        @RequestBody request: TermAgreementRequestDto,
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}

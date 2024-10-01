package me.dgahn.interfaces.controller

import me.dgahn.application.service.QuickConnectCheckService
import me.dgahn.interfaces.dto.QuickConnectConfirmResultDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class QuickConnectController(
    private val quickConnectCheckService: QuickConnectCheckService,
) {
    @GetMapping("/api/v1/quick-connect/availability")
    fun confirmQuickConnect(
        @RequestParam("registrationNumber") registrationNumber: String,
    ): ResponseEntity<QuickConnectConfirmResultDto> {
        return ResponseEntity.ok(QuickConnectConfirmResultDto(quickConnectCheckService.check(registrationNumber)))
    }
}

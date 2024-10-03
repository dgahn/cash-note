package me.dgahn.interfaces.controller

import me.dgahn.infrastructure.client.http.dto.RegisterDataCardRequestDto
import me.dgahn.interfaces.dto.QuickConnectConfirmResultDto
import me.dgahn.interfaces.dto.TermAgreementRequestDto
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping
class CommunityMockController {
    @GetMapping("/has-business")
    fun check(
        @RequestParam("registrationNumber") registrationNumber: String,
    ): ResponseEntity<QuickConnectConfirmResultDto> {
        logger.info { "called mock api, registration: $registrationNumber" }
        return ResponseEntity.ok(QuickConnectConfirmResultDto(true))
    }

    @PostMapping("/register-data-communication")
    fun register(
        @RequestBody request: TermAgreementRequestDto,
    ) {
        logger.info { "called mock api, request: $request" }
    }

    /**
     * 테스트시
     * 최초 request.size가 1100, 어제 100
     * 데일리 request.size가 100 어제 100
     */
    @PostMapping("/register-data-card")
    fun register(
        @RequestBody request: List<RegisterDataCardRequestDto>,
    ) {
        val today = LocalDate.now().minusDays(1)
        logger.info {
            "called mock api, request size: ${request.size}, yesterday: ${request.count {
                it.transactionDate == today
            }}"
        }
    }
}

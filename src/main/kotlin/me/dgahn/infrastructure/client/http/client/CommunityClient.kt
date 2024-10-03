package me.dgahn.infrastructure.client.http.client

import me.dgahn.infrastructure.client.http.config.FeignConfig
import me.dgahn.infrastructure.client.http.dto.HasBusinessResponseDto
import me.dgahn.infrastructure.client.http.dto.RegisterDataCardRequestDto
import me.dgahn.infrastructure.client.http.dto.RegisterDataCommunicationRequestDto
import mu.KotlinLogging
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

private val logger = KotlinLogging.logger { }

@FeignClient(
    name = "community",
    configuration = [FeignConfig::class],
    fallbackFactory = CommunityClient.CommunityClientFallbackFactory::class,
)
interface CommunityClient {
    @GetMapping("/has-business")
    fun check(
        @RequestParam registrationNumber: String,
    ): HasBusinessResponseDto

    @PostMapping("/register-data-communication")
    fun registerDataCommunication(
        @RequestBody request: RegisterDataCommunicationRequestDto,
    )

    @PostMapping("/register-data-card")
    fun registerDataCard(
        @RequestBody request: List<RegisterDataCardRequestDto>,
    )

    @Component
    class CommunityClientFallbackFactory : FallbackFactory<CommunityClient> {
        override fun create(cause: Throwable): CommunityClient = fallbacks(cause)

        private fun fallbacks(cause: Throwable): CommunityClient = object : CommunityClient {
            override fun check(registrationNumber: String): HasBusinessResponseDto {
                logger.warn(cause) {
                    "공동체 check API에서 폴백이 발생하였습니다. " +
                        "requestNumber: $registrationNumber, cause: $cause"
                }
                throw cause
            }

            override fun registerDataCommunication(request: RegisterDataCommunicationRequestDto) {
                logger.warn(cause) {
                    "공동체 check API에서 폴백이 발생하였습니다. " +
                        "request: $request, cause: $cause"
                }
                throw cause
            }

            override fun registerDataCard(request: List<RegisterDataCardRequestDto>) {
                logger.warn(cause) {
                    "공동체 check API에서 폴백이 발생하였습니다. " +
                        "request: $request, cause: $cause"
                }
                throw cause
            }
        }
    }
}

package me.dgahn.infrastructure.client.http.client

import me.dgahn.infrastructure.client.http.config.FeignConfig
import me.dgahn.infrastructure.client.http.dto.RegisterRequestDto
import mu.KotlinLogging
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

private val logger = KotlinLogging.logger { }

@FeignClient(
    name = "community",
    configuration = [FeignConfig::class],
    fallbackFactory = CommunityClient.CommunityClientFallbackFactory::class,
)
interface CommunityClient {
    @GetMapping("/has-business")
    fun check(registrationNumber: String): Boolean

    @PostMapping("/register-data-communication")
    fun register(request: RegisterRequestDto)

    @Component
    class CommunityClientFallbackFactory : FallbackFactory<CommunityClient> {
        override fun create(cause: Throwable): CommunityClient = fallbacks(cause)

        private fun fallbacks(cause: Throwable): CommunityClient = object : CommunityClient {
            override fun check(registrationNumber: String): Boolean {
                logger.warn(cause) {
                    "공동체 check API에서 폴백이 발생하였습니다. " +
                        "requestNumber: $registrationNumber, cause: $cause"
                }
                throw cause
            }

            override fun register(request: RegisterRequestDto) {
                logger.warn(cause) {
                    "공동체 check API에서 폴백이 발생하였습니다. " +
                        "request: $request, cause: $cause"
                }
                throw cause
            }
        }
    }
}

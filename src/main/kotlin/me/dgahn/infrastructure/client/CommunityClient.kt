package me.dgahn.infrastructure.client

import me.dgahn.infrastructure.config.FeignConfig
import mu.KotlinLogging
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping

private val logger = KotlinLogging.logger { }

@FeignClient(
    name = "community",
    configuration = [FeignConfig::class],
    fallbackFactory = CommunityClient.CommunityClientFallbackFactory::class,
)
interface CommunityClient {
    @GetMapping("has-business")
    fun check(registrationNumber: String): Boolean

    @Component
    class CommunityClientFallbackFactory : FallbackFactory<CommunityClient> {
        override fun create(cause: Throwable): CommunityClient = fallbacks(cause)

        private fun fallbacks(cause: Throwable): CommunityClient = object : CommunityClient {
            override fun check(registrationNumber: String): Boolean {
                logger.warn(cause) { "공동체 API 호출에서 폴백이 발생하였습니다. cause: $cause" }
                throw cause
            }
        }
    }
}

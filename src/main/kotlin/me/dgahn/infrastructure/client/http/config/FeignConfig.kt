package me.dgahn.infrastructure.client.http.config

import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {
    @Bean
    fun errorDecoder(): ErrorDecoder = FeignErrorDecoder()
}

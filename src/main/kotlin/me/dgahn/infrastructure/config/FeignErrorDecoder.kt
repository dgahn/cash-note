package me.dgahn.infrastructure.config

import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging
import org.apache.commons.io.IOUtils
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception

private val logger = KotlinLogging.logger {}

class FeignErrorDecoder : ErrorDecoder {
    override fun decode(method: String, response: Response): Exception {
        val message = IOUtils.toString(response.body().asInputStream(), Charsets.UTF_8)

        logger.error { "Http 통신에 에러가 발생하였습니다. http status: ${response.status()}, message: $message" }

        return ResponseStatusException(HttpStatus.valueOf(response.status()))
    }
}

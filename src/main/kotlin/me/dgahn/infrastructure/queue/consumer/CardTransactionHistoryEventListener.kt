package me.dgahn.infrastructure.queue.consumer

import com.fasterxml.jackson.core.type.TypeReference
import me.dgahn.application.service.CardTransactionHistoryCreateService
import me.dgahn.infrastructure.queue.config.RabbitMqConfig.Companion.QUEUE_NAME
import me.dgahn.infrastructure.queue.event.CardTransactionHistoryPayload
import me.dgahn.util.objectMapper
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class CardTransactionHistoryEventListener(
    private val cardTransactionHistoryCreateService: CardTransactionHistoryCreateService,
) {
    @RabbitListener(queues = [QUEUE_NAME])
    fun accept(message: String) {
        try {
            cardTransactionHistoryCreateService.create(
                objectMapper.readValue(message, object : TypeReference<List<CardTransactionHistoryPayload>>() {}),
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

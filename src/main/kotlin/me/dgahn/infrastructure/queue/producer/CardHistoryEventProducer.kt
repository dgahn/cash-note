package me.dgahn.infrastructure.queue.producer

import me.dgahn.infrastructure.queue.event.CardTransactionHistoryPayload
import me.dgahn.util.objectMapper
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class CardHistoryEventProducer(
    private val rabbitTemplate: RabbitTemplate,
    private val queue: Queue
) {
    @Async
    fun send(payload: List<CardTransactionHistoryPayload>) {
        rabbitTemplate.convertAndSend(
            queue.name,
            objectMapper.writeValueAsString(payload)
        )
    }
}

package me.dgahn.application.service

import me.dgahn.domain.service.TermSaver
import me.dgahn.domain.model.Term
import me.dgahn.infrastructure.client.http.client.CommunityClient
import me.dgahn.infrastructure.client.http.dto.toRegisterRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TermCreateService(
    private val termSaver: TermSaver,
    private val communityClient: CommunityClient,
) {
    @Transactional
    fun createTerm(registrationNumber: String, agreedType: Boolean) {
        // 데이터 저장
        val term = Term.create(registrationNumber = registrationNumber, agreedType = agreedType)
        termSaver.save(term)

        // 공동체 API 호출
        communityClient.registerDataCommunication(term.toRegisterRequestDto())
    }
}

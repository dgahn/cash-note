package me.dgahn.domain.service

import me.dgahn.domain.model.Term
import me.dgahn.infrastructure.database.entity.toEntity
import me.dgahn.infrastructure.database.repository.TermRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TermSaver(
    private val termRepository: TermRepository,
) {
    @Transactional
    fun save(term: Term): Term {
        return termRepository.save(term.toEntity()).toDomain()
    }
}

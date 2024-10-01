package me.dgahn.infrastructure.database.repository

import me.dgahn.infrastructure.database.entity.TermEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TermRepository : JpaRepository<TermEntity, String>

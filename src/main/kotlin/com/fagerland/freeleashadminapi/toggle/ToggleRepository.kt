package com.fagerland.freeleashadminapi.toggle

import com.fagerland.freeleashadminapi.toggle.domain.Toggle
import org.springframework.data.jpa.repository.JpaRepository
import javax.transaction.Transactional

interface ToggleRepository: JpaRepository<Toggle, Long> {
    fun existsByNameAndTeamId(name: String, teamId: Long): Boolean

    fun findAllByTeamName(name: String): List<Toggle>

    @Transactional
    fun deleteAllByTeamId(id: Long)
}
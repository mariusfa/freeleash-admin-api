package com.fagerland.freeleashadminapi.toggle.biz.repository.jpa

import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleEntity
import org.springframework.data.jpa.repository.JpaRepository
import javax.transaction.Transactional

interface ToggleRepository : JpaRepository<ToggleEntity, Long> {
    fun existsByNameAndTeamId(name: String, teamId: Long): Boolean

    fun findAllByTeamName(name: String): List<ToggleEntity>

    @Transactional
    fun deleteAllByTeamId(id: Long)
}
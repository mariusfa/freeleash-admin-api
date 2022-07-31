package com.fagerland.freeleashadminapi.toggle

import org.springframework.data.jpa.repository.JpaRepository

interface ToggleRepository: JpaRepository<Toggle, Long> {
    fun existsByNameAndTeamId(name: String, teamId: Long): Boolean

    fun findAllByTeamName(name: String): List<Toggle>
}
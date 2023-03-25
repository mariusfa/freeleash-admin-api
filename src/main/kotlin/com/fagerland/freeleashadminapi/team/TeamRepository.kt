package com.fagerland.freeleashadminapi.team

import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository : JpaRepository<TeamEntity, Long> {
    fun existsByName(name: String): Boolean
}
package com.fagerland.freeleashadminapi.team

import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository : JpaRepository<Team, Long> {
    fun existsByName(name: String): Boolean
}
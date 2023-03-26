package com.fagerland.freeleashadminapi.team.biz.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepositoryJpa : JpaRepository<TeamEntity, Long> {
    fun existsByName(name: String): Boolean
}
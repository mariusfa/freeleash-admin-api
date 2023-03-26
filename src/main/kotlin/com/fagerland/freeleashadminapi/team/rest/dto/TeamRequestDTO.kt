package com.fagerland.freeleashadminapi.team.rest.dto

import com.fagerland.freeleashadminapi.team.biz.domain.TeamRequest

data class TeamRequestDTO(val name: String) {
    fun toDomain(): TeamRequest = TeamRequest(name = name)
}

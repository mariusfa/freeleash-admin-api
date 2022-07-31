package com.fagerland.freeleashadminapi.toggle

data class ToggleRequestDTO(
    val name: String,
    val teamId: Long
) {
    fun toDomain(): ToggleRequest = ToggleRequest(name = name, teamId = teamId)
}

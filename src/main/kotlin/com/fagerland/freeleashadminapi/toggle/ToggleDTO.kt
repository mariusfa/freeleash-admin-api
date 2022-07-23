package com.fagerland.freeleashadminapi.toggle

data class ToggleDTO(
    val id: Long,
    val name: String,
    val team: String,
    val isToggled: Boolean,
    val toggledDistribution: Int,
)

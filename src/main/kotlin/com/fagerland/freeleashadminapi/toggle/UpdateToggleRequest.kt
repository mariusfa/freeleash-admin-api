package com.fagerland.freeleashadminapi.toggle

data class UpdateToggleRequest(
    val id: Long,
    val name: String,
    val isToggled: Boolean
)

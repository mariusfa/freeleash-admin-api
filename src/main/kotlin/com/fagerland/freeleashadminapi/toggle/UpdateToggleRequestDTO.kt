package com.fagerland.freeleashadminapi.toggle

data class UpdateToggleRequestDTO(
    val name: String,
    val isToggled: Boolean
) {
    fun toDomain(id: Long): UpdateToggleRequest = UpdateToggleRequest(id = id, name = name, isToggled = isToggled)
}

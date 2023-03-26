package com.fagerland.freeleashadminapi.toggle.biz.repository.jpa

import com.fagerland.freeleashadminapi.toggle.rest.dto.ToggleOperatorDTO

enum class ToggleOperatorEntity {
    AND, OR;

    fun toDTO(): ToggleOperatorDTO = when (this) {
        AND -> ToggleOperatorDTO.AND
        OR -> ToggleOperatorDTO.OR
    }
}
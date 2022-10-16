package com.fagerland.freeleashadminapi.toggle.domain

import com.fagerland.freeleashadminapi.toggle.dto.ToggleOperatorDTO

enum class ToggleOperator {
    AND, OR;

    fun toDTO(): ToggleOperatorDTO = when (this) {
        AND -> ToggleOperatorDTO.AND
        OR -> ToggleOperatorDTO.OR
    }
}
package com.fagerland.freeleashadminapi.toggle.dto

import com.fagerland.freeleashadminapi.toggle.domain.ToggleOperator

enum class ToggleOperatorDTO {
    AND, OR;

    fun toDomain(): ToggleOperator = when (this) {
        AND -> ToggleOperator.AND
        OR -> ToggleOperator.OR
    }
}
package com.fagerland.freeleashadminapi.toggle

import com.fagerland.freeleashadminapi.toggle.dto.ToggleDTO
import com.fagerland.freeleashadminapi.toggle.dto.ToggleRequestDTO
import com.fagerland.freeleashadminapi.toggle.dto.UpdateToggleRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface ToggleContract {

    @Operation(description = "Get toggles for given team")
    fun getTogglesForTeam(team: String): List<ToggleDTO>

    @Operation(description = "Create toggle")
    fun createToggle(toggleRequestDTO: ToggleRequestDTO)

    @Operation(description = "Update values for toggle")
    fun updateToggle(id: Long, updateToggleRequestDTO: UpdateToggleRequestDTO): ToggleDTO
}
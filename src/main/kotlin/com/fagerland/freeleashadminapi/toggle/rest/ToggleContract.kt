package com.fagerland.freeleashadminapi.toggle.rest

import com.fagerland.freeleashadminapi.toggle.rest.dto.ToggleDTO
import com.fagerland.freeleashadminapi.toggle.rest.dto.ToggleRequestDTO
import com.fagerland.freeleashadminapi.toggle.rest.dto.UpdateToggleRequestDTO
import io.swagger.v3.oas.annotations.Operation

interface ToggleContract {

    @Operation(description = "Get toggles for given team")
    fun getTogglesForTeam(team: String): List<ToggleDTO>

    @Operation(description = "Create toggle")
    fun createToggle(toggleRequestDTO: ToggleRequestDTO)

    @Operation(description = "Update values for toggle")
    fun updateToggle(id: Long, updateToggleRequestDTO: UpdateToggleRequestDTO): ToggleDTO

    @Operation(description = "Get a toggle given id")
    fun getToggle(id: Long): ToggleDTO

    @Operation(description = "Delete a toggle given id")
    fun deleteToggle(id: Long)
}
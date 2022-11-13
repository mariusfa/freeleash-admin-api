package com.fagerland.freeleashadminapi.team

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface TeamContract {

    @Operation(description = "Get a list of teams")
    fun listTeams(): List<TeamDTO>

    @Operation(description = "Create a team")
    fun createTeam(teamRequestDTO: TeamRequestDTO)

    @Operation(description = "Update team info")
    fun updateTeam(teamRequestDTO: TeamRequestDTO, id: Long)

    @Operation(description = "Delete a team given id")
    fun deleteTeam(id: Long)
}
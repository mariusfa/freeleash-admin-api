package com.fagerland.freeleashadminapi.team.rest

import com.fagerland.freeleashadminapi.team.rest.dto.TeamDTO
import com.fagerland.freeleashadminapi.team.rest.dto.TeamRequestDTO
import io.swagger.v3.oas.annotations.Operation

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
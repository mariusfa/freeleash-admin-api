package com.fagerland.freeleashadminapi.team

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/team")
class TeamController(private val teamService: TeamService) : TeamContract {

    @GetMapping
    fun listTeams(): List<TeamDTO> = teamService.findTeams().map { TeamDTO(id = it.id!!, name = it.name) }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createTeam(@RequestBody teamRequestDTO: TeamRequestDTO) = teamService.createTeam(teamRequestDTO.name)

    @PutMapping("/{id}")
    fun updateTeam(@RequestBody teamRequestDTO: TeamRequestDTO, @PathVariable id: Long) =
        teamService.updateTeam(id, teamRequestDTO.name)

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteTeam(@PathVariable id: Long) = teamService.deleteTeam(id)
}
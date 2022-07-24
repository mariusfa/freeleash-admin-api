package com.fagerland.freeleashadminapi.team

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/team")
class TeamController(private val timeService: TeamService) {

    @GetMapping
    fun listTeams(): List<TeamDTO> = timeService.findTeams().map { TeamDTO(id = it.id!!,name = it.name) }

    @PostMapping
    fun createTeam() = "TODO"

    @PutMapping
    fun updateTeam() = "TODO"

    @DeleteMapping
    fun deleteTeam() = "TODO"
}
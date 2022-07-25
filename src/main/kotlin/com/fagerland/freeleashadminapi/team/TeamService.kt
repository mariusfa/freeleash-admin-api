package com.fagerland.freeleashadminapi.team

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TeamService(
    private val teamRepository: TeamRepository
) {
    fun findTeams(): MutableIterable<Team> = teamRepository.findAll()

    fun createTeam(name: String) = teamRepository.save(Team(name = name))

    fun updateTeam(id: Long, name: String) {
        val existingTeam: Team = teamRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id: ${id} not found") }
        existingTeam.name = name
        teamRepository.save(existingTeam)
    }
}
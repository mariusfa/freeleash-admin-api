package com.fagerland.freeleashadminapi.team.biz

import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamEntity
import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamRepositoryJpa
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleRepositoryJpa
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TeamService(
    private val teamRepositoryJpa: TeamRepositoryJpa,
    private val toggleRepositoryJpa: ToggleRepositoryJpa
) {
    fun findTeams(): MutableIterable<TeamEntity> = teamRepositoryJpa.findAll()

    fun createTeam(name: String) {
        if (teamRepositoryJpa.existsByName(name)) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "Team with name: $name already exists"
        )
        teamRepositoryJpa.save(TeamEntity(name = name))
    }

    fun updateTeam(id: Long, name: String) {
        val existingTeamEntity: TeamEntity = teamRepositoryJpa.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id: $id not found") }
        if (teamRepositoryJpa.existsByName(name) && existingTeamEntity.name != name) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "Team with name: $name already exists"
        )

        existingTeamEntity.name = name
        teamRepositoryJpa.save(existingTeamEntity)
    }

    fun deleteTeam(id: Long) {
        if (!teamRepositoryJpa.existsById(id)) throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Team with id: $id not found"
        )
        toggleRepositoryJpa.deleteAllByTeamId(id)
        teamRepositoryJpa.deleteById(id)
    }
}
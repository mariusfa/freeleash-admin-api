package com.fagerland.freeleashadminapi.team

import com.fagerland.freeleashadminapi.toggle.ToggleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TeamService(
    private val teamRepository: TeamRepository,
    private val toggleRepository: ToggleRepository
) {
    fun findTeams(): MutableIterable<TeamEntity> = teamRepository.findAll()

    fun createTeam(name: String) {
        if (teamRepository.existsByName(name)) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "Team with name: $name already exists"
        )
        teamRepository.save(TeamEntity(name = name))
    }

    fun updateTeam(id: Long, name: String) {
        val existingTeamEntity: TeamEntity = teamRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id: $id not found") }
        if (teamRepository.existsByName(name) && existingTeamEntity.name != name) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "Team with name: $name already exists"
        )

        existingTeamEntity.name = name
        teamRepository.save(existingTeamEntity)
    }

    fun deleteTeam(id: Long) {
        if (!teamRepository.existsById(id)) throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Team with id: $id not found"
        )
        toggleRepository.deleteAllByTeamId(id)
        teamRepository.deleteById(id)
    }
}
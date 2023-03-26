package com.fagerland.freeleashadminapi.toggle.biz

import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamRepositoryJpa
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ConditionEntity
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleEntity
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleRepositoryJpa
import com.fagerland.freeleashadminapi.toggle.biz.domain.ToggleRequest
import com.fagerland.freeleashadminapi.toggle.biz.domain.UpdateToggleRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ToggleService(
    private val toggleRepositoryJpa: ToggleRepositoryJpa,
    private val teamRepositoryJpa: TeamRepositoryJpa
) {
    fun createToggle(toggleRequest: ToggleRequest) {
        val team = teamRepositoryJpa.findById(toggleRequest.teamId).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Team not found for id: ${toggleRequest.teamId}"
            )
        }
        if (toggleRepositoryJpa.existsByNameAndTeamId(toggleRequest.name, toggleRequest.teamId)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Toggle with name: ${toggleRequest.name} already exists")
        }

        val newToggleEntity = ToggleEntity(
            name = toggleRequest.name,
            team = team,
            isToggled = toggleRequest.isToggled,
            operator = toggleRequest.operator,
            conditions = if (toggleRequest.conditions.isEmpty()) mutableSetOf() else toggleRequest.conditions as MutableSet<ConditionEntity>
        )
        toggleRepositoryJpa.save(newToggleEntity)
    }

    fun listTogglesForTeam(teamName: String): List<ToggleEntity> {
        if (!teamRepositoryJpa.existsByName(teamName)) throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Team not found with name $teamName"
        )
        return toggleRepositoryJpa.findAllByTeamName(teamName)
    }

    fun updateToggle(updateToggleRequest: UpdateToggleRequest): ToggleEntity {
        val toggle = toggleRepositoryJpa.findById(updateToggleRequest.id).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Toggle with id ${updateToggleRequest.id} not found"
            )
        }
        if (toggleRepositoryJpa.existsByNameAndTeamId(
                updateToggleRequest.name,
                toggle.team.id!!
            ) && updateToggleRequest.name != toggle.name
        ) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Toggle with name: ${updateToggleRequest.name} already exists"
            )
        }

        toggle.name = updateToggleRequest.name
        toggle.isToggled = updateToggleRequest.isToggled
        toggle.operator = updateToggleRequest.operator
        toggle.conditions.clear()
        toggle.conditions.addAll(updateToggleRequest.conditions)
        return toggleRepositoryJpa.save(toggle)
    }

    fun getToggle(id: Long): ToggleEntity = toggleRepositoryJpa.findById(id).orElseThrow {
        ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Toggle with id $id not found"
        )
    }

    fun deleteToggle(id: Long) {
        if (!toggleRepositoryJpa.existsById(id)) throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Toggle not found with id $id"
        )
        toggleRepositoryJpa.deleteById(id)
    }
}
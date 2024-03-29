package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamEntity
import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamRepositoryJpa
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleRepositoryJpa
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleEntity
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ToggleTest(
    @Autowired private val mvc: MockMvc,
    @Autowired private val teamRepositoryJpa: TeamRepositoryJpa,
    @Autowired private val toggleRepositoryJpa: ToggleRepositoryJpa
) {

    @Test
    @Transactional
    fun `should test create toggle`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        mvc.perform(
            MockMvcRequestBuilders.post("/toggle")
                .content(
                    """
                    {
                        "name": "test-toggle",
                        "teamId": ${teamEntity.id},
                        "operator": "AND",
                        "isToggled": true,
                        "conditions": [
                            {
                                "field": "userId",
                                "operator": "IN",
                                "contents" : ["123"]
                            }
                        ]
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
        val toggles = toggleRepositoryJpa.findAll()
        assertEquals(1, toggles.size)
        assertEquals("test-toggle", toggles[0].name)
        assertEquals(teamEntity.id, toggles[0].team.id)
        assertEquals(true, toggles[0].isToggled)
    }

    @Test
    @Transactional
    fun `should test create toggle name conflict`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        toggleRepositoryJpa.saveAndFlush(
            ToggleEntity(
            name = "test-toggle",
            team = teamEntity,
            isToggled = true,
        )
        )
        mvc.perform(
            MockMvcRequestBuilders.post("/toggle")
                .content(
                    """
                    {
                        "name": "test-toggle",
                        "teamId": ${teamEntity.id},
                        "operator": "AND",
                        "isToggled": true,
                        "conditions": []
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isConflict)
        val togglesFound = toggleRepositoryJpa.findAll()
        assertEquals(1, togglesFound.size)
    }

    @Test
    @Transactional
    fun `should test update toggle`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        val toggleEntityToUpdate = toggleRepositoryJpa.saveAndFlush(
            ToggleEntity(
            name = "test-toggle",
            team = teamEntity,
            isToggled = true,
        )
        )
        mvc.perform(
            MockMvcRequestBuilders.put("/toggle/${toggleEntityToUpdate.id}")
                .content(
                    """
                    {
                        "name": "test-toggle",
                        "teamId": ${teamEntity.id},
                        "operator": "AND",
                        "isToggled": false,
                        "conditions": []
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        val toggleFound = toggleRepositoryJpa.findById(toggleEntityToUpdate.id!!).get()
        assertFalse(toggleFound.isToggled)
    }

    @Test
    @Transactional
    fun `should test update toggle to name which already exists`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        val toggleEntityToUpdate = toggleRepositoryJpa.saveAndFlush(
            ToggleEntity(
            name = "test-toggle",
            team = teamEntity,
            isToggled = true,
        )
        )
        val toggleEntityToConflictWith = toggleRepositoryJpa.saveAndFlush(
            ToggleEntity(
            name = "conflict-toggle",
            team = teamEntity,
            isToggled = true,
        )
        )
        mvc.perform(
            MockMvcRequestBuilders.put("/toggle/${toggleEntityToUpdate.id}")
                .content(
                    """
                    {
                        "name": "conflict-toggle",
                        "teamId": ${teamEntity.id},
                        "operator": "AND",
                        "isToggled": false,
                        "conditions": []
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isConflict)

        val toggleFound = toggleRepositoryJpa.findById(toggleEntityToUpdate.id!!).get()
        assertNotEquals(toggleEntityToConflictWith.name, toggleFound.name)
    }

    @Test
    @Transactional
    fun `should test toggle delete`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        val toggleEntityToDelete = toggleRepositoryJpa.saveAndFlush(
            ToggleEntity(
            name = "test-toggle",
            team = teamEntity,
            isToggled = true,
        )
        )
        mvc.perform(
            MockMvcRequestBuilders.delete("/toggle/${toggleEntityToDelete.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andExpect(
                MockMvcResultMatchers.content().string("")
            )

        val toggleFound = toggleRepositoryJpa.findById(toggleEntityToDelete.id!!)
        assertFalse(toggleFound.isPresent)
    }

    @Test
    @Transactional
    fun `should test list toggle for team`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        val toggleEntity = toggleRepositoryJpa.saveAndFlush(
            ToggleEntity(
            name = "test-toggle",
            team = teamEntity,
            isToggled = true,
        )
        )
        mvc.perform(
            MockMvcRequestBuilders.get("/toggle").queryParam("team", teamEntity.name)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Int>(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.`is`(toggleEntity.name)))
    }

    @Test
    @Transactional
    fun `should test get toggle given id`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        val toggleEntity = toggleRepositoryJpa.saveAndFlush(
            ToggleEntity(
            name = "test-toggle",
            team = teamEntity,
            isToggled = true,
        )
        )
        mvc.perform(
            MockMvcRequestBuilders.get("/toggle/${toggleEntity.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.`is`(toggleEntity.name)))
    }
}
package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.Team
import com.fagerland.freeleashadminapi.team.TeamRepository
import com.fagerland.freeleashadminapi.toggle.ToggleRepository
import com.fagerland.freeleashadminapi.toggle.domain.Toggle
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
    @Autowired private val teamRepository: TeamRepository,
    @Autowired private val toggleRepository: ToggleRepository
) {

    @Test
    @Transactional
    fun `should test create toggle`() {
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        mvc.perform(
            MockMvcRequestBuilders.post("/toggle")
                .content(
                    """
                    {
                        "name": "test-toggle",
                        "teamId": ${team.id},
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
        val toggles = toggleRepository.findAll()
        assertEquals(1, toggles.size)
        assertEquals("test-toggle", toggles[0].name)
        assertEquals(team.id, toggles[0].team.id)
        assertEquals(true, toggles[0].isToggled)
    }

    @Test
    @Transactional
    fun `should test create toggle name conflict`() {
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        toggleRepository.saveAndFlush(Toggle(
            name = "test-toggle",
            team = team,
            isToggled = true,
        ))
        mvc.perform(
            MockMvcRequestBuilders.post("/toggle")
                .content(
                    """
                    {
                        "name": "test-toggle",
                        "teamId": ${team.id},
                        "operator": "AND",
                        "isToggled": true,
                        "conditions": []
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isConflict)
        val togglesFound = toggleRepository.findAll()
        assertEquals(1, togglesFound.size)
    }

    @Test
    @Transactional
    fun `should test update toggle`() {
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        val toggleToUpdate = toggleRepository.saveAndFlush(Toggle(
            name = "test-toggle",
            team = team,
            isToggled = true,
        ))
        mvc.perform(
            MockMvcRequestBuilders.put("/toggle/${toggleToUpdate.id}")
                .content(
                    """
                    {
                        "name": "test-toggle",
                        "teamId": ${team.id},
                        "operator": "AND",
                        "isToggled": false,
                        "conditions": []
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        val toggleFound = toggleRepository.findById(toggleToUpdate.id!!).get()
        assertFalse(toggleFound.isToggled)
    }

    @Test
    @Transactional
    fun `should test update toggle to name which already exists`() {
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        val toggleToUpdate = toggleRepository.saveAndFlush(Toggle(
            name = "test-toggle",
            team = team,
            isToggled = true,
        ))
        val toggleToConflictWith = toggleRepository.saveAndFlush(Toggle(
            name = "conflict-toggle",
            team = team,
            isToggled = true,
        ))
        mvc.perform(
            MockMvcRequestBuilders.put("/toggle/${toggleToUpdate.id}")
                .content(
                    """
                    {
                        "name": "conflict-toggle",
                        "teamId": ${team.id},
                        "operator": "AND",
                        "isToggled": false,
                        "conditions": []
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isConflict)

        val toggleFound = toggleRepository.findById(toggleToUpdate.id!!).get()
        assertNotEquals(toggleToConflictWith.name, toggleFound.name)
    }

    @Test
    @Transactional
    fun `should test toggle delete`() {
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        val toggleToDelete = toggleRepository.saveAndFlush(Toggle(
            name = "test-toggle",
            team = team,
            isToggled = true,
        ))
        mvc.perform(
            MockMvcRequestBuilders.delete("/toggle/${toggleToDelete.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andExpect(
                MockMvcResultMatchers.content().string("")
            )

        val toggleFound = toggleRepository.findById(toggleToDelete.id!!)
        assertFalse(toggleFound.isPresent)
    }

    @Test
    @Transactional
    fun `should test list toggle for team`() {
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        val toggle = toggleRepository.saveAndFlush(Toggle(
            name = "test-toggle",
            team = team,
            isToggled = true,
        ))
        mvc.perform(
            MockMvcRequestBuilders.get("/toggle").queryParam("team", team.name)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Int>(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.`is`(toggle.name)))
    }

    @Test
    @Transactional
    fun `should test get toggle given id`() {
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        val toggle = toggleRepository.saveAndFlush(Toggle(
            name = "test-toggle",
            team = team,
            isToggled = true,
        ))
        mvc.perform(
            MockMvcRequestBuilders.get("/toggle/${toggle.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.`is`(toggle.name)))
    }
}
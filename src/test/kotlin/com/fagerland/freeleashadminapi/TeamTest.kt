package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamEntity
import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamRepositoryJpa
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TeamTest(@Autowired private val mvc: MockMvc, @Autowired private val teamRepositoryJpa: TeamRepositoryJpa) {

    @Test
    @Transactional
    fun `should test create team`() {
        mvc.perform(
            post("/team")
                .content(
                    """
                    {
                        "name": "test-team"
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)

        val teams = teamRepositoryJpa.findAll()
        assertTrue(teams.any { it.name == "test-team" })
    }

    @Test
    @Transactional
    fun `should test create team name conflict`() {
        teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        mvc.perform(
            post("/team")
                .content(
                    """
                    {
                        "name": "test-team"
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isConflict)

        val teams = teamRepositoryJpa.findAll()
        assertEquals(1, teams.size)
    }

    @Test
    @Transactional
    fun `should test list teams`() {
        teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        mvc.perform(
            get("/team")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name", `is`("test-team")))
            .andExpect(jsonPath("$", hasSize<Int>(1)))
    }


    @Test
    @Transactional
    fun `should test update team`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        mvc.perform(
            put("/team/${teamEntity.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "name": "test-team1"
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isOk)
            .andExpect(
                content().string("")
            )
        val teamFound = teamRepositoryJpa.findById(teamEntity.id!!).get()
        assertEquals("test-team1", teamFound.name)
    }

    @Test
    @Transactional
    fun `should test update team name conflict`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team1"))
        mvc.perform(
            put("/team/${teamEntity.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "name": "test-team1"
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isConflict)

        val teamFound = teamRepositoryJpa.findById(teamEntity.id!!).get()
        assertEquals(teamEntity.name, teamFound.name)
    }


    @Test
    @Transactional
    fun `should test delete team`() {
        val teamEntity = teamRepositoryJpa.saveAndFlush(TeamEntity(name = "test-team"))
        mvc.perform(
            delete("/team/${teamEntity.id}")
        )
            .andExpect(status().isNoContent)
            .andExpect(
                content().string("")
            )
        val teamFound = teamRepositoryJpa.findById(teamEntity.id!!)
        assertFalse(teamFound.isPresent)
    }
}
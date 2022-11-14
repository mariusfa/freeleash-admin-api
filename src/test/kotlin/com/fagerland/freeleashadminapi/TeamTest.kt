package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.Team
import com.fagerland.freeleashadminapi.team.TeamRepository
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TeamTest(@Autowired private val mvc: MockMvc, @Autowired private val teamRepository: TeamRepository) {

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

        val teams = teamRepository.findAll()
        assertTrue(teams.any { it.name == "test-team" })
    }

    @Test
    @Transactional
    fun `should test list teams`() {
        teamRepository.saveAndFlush(Team(name = "test-team"))
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
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        mvc.perform(
            put("/team/${team.id}")
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
        val teamFound = teamRepository.findById(team.id!!).get()
        assertEquals("test-team1", teamFound.name)
    }


    @Test
    @Transactional
    fun `should test delete team`() {
        val team = teamRepository.saveAndFlush(Team(name = "test-team"))
        mvc.perform(
            delete("/team/${team.id}")
        )
            .andExpect(status().isNoContent)
            .andExpect(
                content().string("")
            )
        val teamFound = teamRepository.findById(team.id!!)
        assertFalse(teamFound.isPresent)
    }
}
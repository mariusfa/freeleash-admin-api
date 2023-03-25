package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.Team
import com.fagerland.freeleashadminapi.team.TeamRepository
import com.fagerland.freeleashadminapi.toggle.ToggleRepository
import com.fagerland.freeleashadminapi.toggle.domain.Condition
import com.fagerland.freeleashadminapi.toggle.domain.ConditionOperator
import com.fagerland.freeleashadminapi.toggle.domain.Toggle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.jdbc.core.JdbcTemplate

@DataJpaTest
class ToggleRepositoryTest {

    @Autowired
    private lateinit var teamRepository: TeamRepository

    @Autowired
    private lateinit var toggleRepository: ToggleRepository

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should inject components`() {
        assertThat(teamRepository).isNotNull
        assertThat(toggleRepository).isNotNull
        assertThat(jdbcTemplate).isNotNull
    }

    @Test
    fun `should create toggle`() {
        val savedTeam = teamRepository.save(Team(name = "testing"))
        toggleRepository.save(Toggle(name = "test-toggle", team = savedTeam, isToggled = false))
        val listOfToggles = jdbcTemplate.queryForList("select * from toggle")
        assertThat(listOfToggles).hasSize(1)
        val toggle = listOfToggles[0]
        assertThat(toggle["NAME"]).isEqualTo("test-toggle")
    }

    @Test
    fun `should save toggle and it's conditions`() {
        val savedTeam = teamRepository.save(Team(name = "testing"))
        val savedToggle = toggleRepository.saveAndFlush(Toggle(name = "test-toggle", team = savedTeam, isToggled = false, conditions = mutableSetOf(
            Condition(field = "test-condition", operator = ConditionOperator.IN, contents = setOf("1", "2"))
        )))
        val listOfToggles = jdbcTemplate.queryForList("select * from toggle")
        assertThat(listOfToggles).hasSize(1)

        val listOfConditions = jdbcTemplate.queryForList("select * from condition")
        assertThat(listOfConditions).hasSize(1)
        assertThat(listOfConditions.first()["FIELD"]).isEqualTo("test-condition")

        val listOfContents = jdbcTemplate.queryForList("select * from content")
        assertThat(listOfContents).hasSize(2)
        assertThat(listOfContents[0]["contents"]).isEqualTo("1")
        assertThat(listOfContents[1]["contents"]).isEqualTo("2")

        toggleRepository.deleteById(savedToggle.id!!)
    }

    @Test
    fun `should delete toggle and it's conditions`() {
        val savedTeam = teamRepository.save(Team(name = "testing"))
        val savedToggle = toggleRepository.saveAndFlush(Toggle(name = "test-toggle", team = savedTeam, isToggled = false, conditions = mutableSetOf(
            Condition(field = "test-condition", operator = ConditionOperator.IN, contents = setOf("1", "2"))
        )))
        toggleRepository.deleteById(savedToggle.id!!)
        toggleRepository.flush()

        val listOfToggles = jdbcTemplate.queryForList("select * from toggle")
        assertThat(listOfToggles).hasSize(0)

        val listOfConditions = jdbcTemplate.queryForList("select * from condition")
        assertThat(listOfConditions).hasSize(0)

        val listOfContents = jdbcTemplate.queryForList("select * from content")
        assertThat(listOfContents).hasSize(0)
    }

    @Test
    fun `should only some of content for condition`() {
        val savedTeam = teamRepository.save(Team(name = "testing"))
        val savedToggle = toggleRepository.saveAndFlush(Toggle(name = "test-toggle", team = savedTeam, isToggled = false, conditions = mutableSetOf(
            Condition(field = "test-condition", operator = ConditionOperator.IN, contents = setOf("1", "2"))
        )))

        savedToggle.conditions.clear()
        savedToggle.conditions.add(Condition(field = "test-condition", operator = ConditionOperator.IN, contents = setOf("1")))
        toggleRepository.saveAndFlush(savedToggle)

        val listOfToggles = jdbcTemplate.queryForList("select * from toggle")
        assertThat(listOfToggles).hasSize(1)

        val listOfConditions = jdbcTemplate.queryForList("select * from condition")
        assertThat(listOfConditions).hasSize(1)

        val listOfContents = jdbcTemplate.queryForList("select * from content")
        assertThat(listOfContents).hasSize(1)
    }
}
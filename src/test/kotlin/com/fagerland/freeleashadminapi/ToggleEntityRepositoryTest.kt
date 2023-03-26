package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamEntity
import com.fagerland.freeleashadminapi.team.biz.repository.jpa.TeamRepository
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleRepository
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ConditionEntity
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ConditionOperator
import com.fagerland.freeleashadminapi.toggle.biz.repository.jpa.ToggleEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@ActiveProfiles("test")
@Testcontainers
class ToggleEntityRepositoryTest {

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
        val savedTeamEntity = teamRepository.save(TeamEntity(name = "testing"))
        toggleRepository.save(ToggleEntity(name = "test-toggle", team = savedTeamEntity, isToggled = false))
        val listOfToggles = jdbcTemplate.queryForList("select * from toggle")
        assertThat(listOfToggles).hasSize(1)
        val toggle = listOfToggles[0]
        assertThat(toggle["NAME"]).isEqualTo("test-toggle")
    }

    @Test
    fun `should save toggle and it's conditions`() {
        val savedTeamEntity = teamRepository.save(TeamEntity(name = "testing"))
        val savedToggleEntity = toggleRepository.saveAndFlush(
            ToggleEntity(name = "test-toggle", team = savedTeamEntity, isToggled = false, conditions = mutableSetOf(
            ConditionEntity(field = "test-condition", operator = ConditionOperator.IN, contents = setOf("1", "2"))
        ))
        )
        val listOfToggles = jdbcTemplate.queryForList("select * from toggle")
        assertThat(listOfToggles).hasSize(1)

        val listOfConditions = jdbcTemplate.queryForList("select * from condition")
        assertThat(listOfConditions).hasSize(1)
        assertThat(listOfConditions.first()["FIELD"]).isEqualTo("test-condition")

        val listOfContents = jdbcTemplate.queryForList("select * from content")
        assertThat(listOfContents).hasSize(2)
        assertThat(listOfContents[0]["contents"]).isEqualTo("1")
        assertThat(listOfContents[1]["contents"]).isEqualTo("2")

        toggleRepository.deleteById(savedToggleEntity.id!!)
    }

    @Test
    fun `should delete toggle and it's conditions`() {
        val savedTeamEntity = teamRepository.save(TeamEntity(name = "testing"))
        val savedToggleEntity = toggleRepository.saveAndFlush(
            ToggleEntity(name = "test-toggle", team = savedTeamEntity, isToggled = false, conditions = mutableSetOf(
            ConditionEntity(field = "test-condition", operator = ConditionOperator.IN, contents = setOf("1", "2"))
        ))
        )
        toggleRepository.deleteById(savedToggleEntity.id!!)
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
        val savedTeamEntity = teamRepository.save(TeamEntity(name = "testing"))
        val savedToggleEntity = toggleRepository.saveAndFlush(
            ToggleEntity(name = "test-toggle", team = savedTeamEntity, isToggled = false, conditions = mutableSetOf(
            ConditionEntity(field = "test-condition", operator = ConditionOperator.IN, contents = setOf("1", "2"))
        ))
        )

        savedToggleEntity.conditions.clear()
        savedToggleEntity.conditions.add(ConditionEntity(field = "test-condition", operator = ConditionOperator.IN, contents = setOf("1")))
        toggleRepository.saveAndFlush(savedToggleEntity)

        val listOfToggles = jdbcTemplate.queryForList("select * from toggle")
        assertThat(listOfToggles).hasSize(1)

        val listOfConditions = jdbcTemplate.queryForList("select * from condition")
        assertThat(listOfConditions).hasSize(1)

        val listOfContents = jdbcTemplate.queryForList("select * from content")
        assertThat(listOfContents).hasSize(1)
    }
}
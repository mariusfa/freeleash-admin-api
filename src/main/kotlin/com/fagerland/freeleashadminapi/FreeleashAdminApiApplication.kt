package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.Team
import com.fagerland.freeleashadminapi.team.TeamRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class FreeleashAdminApiApplication

fun main(args: Array<String>) {
	runApplication<FreeleashAdminApiApplication>(*args)
}

@Configuration
class FreeleashConfiguration {

    @Bean
    fun databaseInitializer(teamRepository: TeamRepository) = ApplicationRunner {
        teamRepository.save(Team(name = "default"))
    }
}
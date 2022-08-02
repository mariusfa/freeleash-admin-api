package com.fagerland.freeleashadminapi

import com.fagerland.freeleashadminapi.team.Team
import com.fagerland.freeleashadminapi.team.TeamRepository
import com.fagerland.freeleashadminapi.toggle.Toggle
import com.fagerland.freeleashadminapi.toggle.ToggleRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class FreeleashAdminApiApplication

fun main(args: Array<String>) {
    runApplication<FreeleashAdminApiApplication>(*args)
}

@Configuration
class FreeleashConfiguration {

    @Bean
    fun databaseInitializer(teamRepository: TeamRepository, toggleRepository: ToggleRepository) = ApplicationRunner {
        val team = teamRepository.save(Team(name = "default"))
        toggleRepository.save(Toggle(name = "default", team = team, isToggled = false))
    }

    @Bean
    fun addCorsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedMethods("*")
            }
        }
    }

}
package com.github.dromanenko.backend.base.configuration

import com.github.dromanenko.backend.service.AuthService
import com.github.dromanenko.backend.service.RecipesService
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

interface BaseTestConfiguration : WebMvcConfigurer {
    @Bean
    fun getRecipesService(): RecipesService
    
    @Bean
    fun getAuthorizationService(): AuthService
}
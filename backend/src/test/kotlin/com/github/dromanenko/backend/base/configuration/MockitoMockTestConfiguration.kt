package com.github.dromanenko.backend.base.configuration

import com.github.dromanenko.backend.service.AuthService
import com.github.dromanenko.backend.service.RecipesService
import org.mockito.kotlin.mock
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
@ConditionalOnProperty(value = ["test.configuration"], havingValue = "mockito")
class MockitoMockTestConfiguration : BaseTestConfiguration {
    
    @Bean
    override fun getRecipesService(): RecipesService = mock()

    @Bean
    override fun getAuthorizationService(): AuthService = mock()
}
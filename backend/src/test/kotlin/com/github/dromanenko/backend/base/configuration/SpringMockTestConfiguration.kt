package com.github.dromanenko.backend.base.configuration

import com.github.dromanenko.backend.service.AuthService
import com.github.dromanenko.backend.service.RecipesService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean

@TestConfiguration
@ConditionalOnProperty(value = ["test.configuration"], havingValue = "spring", matchIfMissing = true)
class SpringMockTestConfiguration : BaseTestConfiguration {

    @MockBean
    private lateinit var recipesService: RecipesService

    @MockBean
    private lateinit var authService: AuthService
    
    override fun getRecipesService() = recipesService
    
    override fun getAuthorizationService() = authService
}
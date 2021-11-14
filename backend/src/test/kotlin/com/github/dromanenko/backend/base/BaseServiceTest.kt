package com.github.dromanenko.backend.base

import com.github.dromanenko.backend.repository.RecipesRepository
import com.github.dromanenko.backend.repository.UsersRepository
import org.junit.jupiter.api.AfterEach
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoMoreInteractions

abstract class BaseServiceTest {
    protected val mockUsersRepo = mock<UsersRepository>()
    protected val mockRecipesRepo = mock<RecipesRepository>()

    @AfterEach
    fun noMoreInteractions() {
        verifyNoMoreInteractions(mockUsersRepo)
        verifyNoMoreInteractions(mockRecipesRepo)
    }
}
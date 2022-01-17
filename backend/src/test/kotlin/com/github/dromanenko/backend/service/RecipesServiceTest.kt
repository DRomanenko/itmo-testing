package com.github.dromanenko.backend.service

import com.github.dromanenko.backend.base.BaseServiceTest
import com.github.dromanenko.backend.base.TestModels
import com.github.dromanenko.backend.entity.Recipe
import com.github.dromanenko.backend.request.AddRecipeRequest
import com.github.dromanenko.backend.response.RecipesResponse
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.junit4.DisplayName

@Epic("Backend: Service Tests")
@Feature("Recipes Tests")
@DisplayName("Backend: Service Recipes Tests")
class RecipesServiceTest : BaseServiceTest() {
    private val recipesService = RecipesService(
        mockUsersRepo,
        mockRecipesRepo
    )

    @Test
    @Story("Add recipe")
    @DisplayName("Add recipe correct")
    fun `correct - add recipe`() {
        val login = "user"
        lateinit var recipe: Recipe
        val user = TestModels.user(2)
        whenever(mockUsersRepo.findUserByLogin(login)).thenReturn(user)
        `when`(mockRecipesRepo.save(any())).then { recipe = it.arguments[0] as Recipe; return@then recipe }
        recipesService.addRecipe(login, AddRecipeRequest(name = "name", description = "description"))
        assertEquals(0, recipe.id)
        assertEquals("name", recipe.name)
        assertEquals("description", recipe.description)
        assertEquals(2, recipe.owner.id)
        verify(mockUsersRepo).findUserByLogin(login)
        verify(mockRecipesRepo).save(recipe)
    }

    @Test
    @Story("Get recipe")
    @DisplayName("Get recipe correct")
    fun `correct - get recipes`() {
        whenever(mockRecipesRepo.findAll()).thenReturn(listOf(TestModels.recipe(1, 1), TestModels.recipe(2, 2)))
        assertEquals(
            RecipesResponse(
                recipes = listOf(TestModels.recipeResponseModel(1, 1), TestModels.recipeResponseModel(2, 2)),
                addedRecipes = listOf()
            ),
            recipesService.getRecipes(null)
        )
        verify(mockRecipesRepo).findAll()
    }

    @Test
    @Story("Add recipe")
    @DisplayName("Add recipe correct (authorized)")
    fun `correct - get recipes by user`() {
        val login = "user"
        whenever(mockRecipesRepo.findAllByOwnerLogin(login))
            .thenReturn(listOf(TestModels.recipe(1, 1)))
        whenever(mockUsersRepo.findUserByLogin(login))
            .thenReturn(TestModels.user(1, TestModels.recipe(2, 2), TestModels.recipe(3, 3)))
        assertEquals(
            RecipesResponse(
                recipes = listOf(TestModels.recipeResponseModel(1, 1)),
                addedRecipes = listOf(
                    TestModels.recipeResponseModel(2, 2),
                    TestModels.recipeResponseModel(3, 3),
                )
            ),
            recipesService.getRecipes(login)
        )
        verify(mockRecipesRepo).findAllByOwnerLogin(login)
        verify(mockUsersRepo).findUserByLogin(login)
    }
}
package com.github.dromanenko.backend.system

import com.github.dromanenko.backend.base.AllureHelper
import com.github.dromanenko.backend.base.BaseSystemTest
import com.github.dromanenko.backend.response.RecipeResponse
import com.github.dromanenko.backend.response.RecipesResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.Description
import io.qameta.allure.junit4.DisplayName

@Epic("Backend: System Tests")
@Feature("Recipes Tests")
@DisplayName("Backend: System Recipes Tests")
class RecipesSystemTest : BaseSystemTest() {
    @Test
    @Story("User must see recipes")
    @DisplayName("Add and get recipes")
    @Description("Register, add recipes and check that recipe exists")
    fun `correct - add and get recipes`() {
        val cookie = AllureHelper.step("Correct - register") {
            val registerResponse = register("user", "password", "http://localhost/")
            assertEquals(HttpStatus.FOUND, registerResponse.statusCode)
            assertEquals("http://localhost/", registerResponse.headers.location.toString())
            registerResponse.headers.getFirst(HttpHeaders.SET_COOKIE)!!
        }

        AllureHelper.step("Correct - add recipe with session") {
            val addRecipesResponse = addRecipe("name", "description", cookie)
            assertEquals(HttpStatus.OK, addRecipesResponse.statusCode)
        }

        AllureHelper.step("Correct - get user recipes with session") {
            val getMyRecipesResponse = getRecipes("user", cookie)
            assertEquals(HttpStatus.OK, getMyRecipesResponse.statusCode)
            getMyRecipesResponse.body!!.recipes.single().id = 0
            assertEquals(
                RecipesResponse(
                    recipes = listOf(RecipeResponse(0, "name", "description", "user")),
                    addedRecipes = listOf()
                ), getMyRecipesResponse.body
            )
        }

        AllureHelper.step("Correct - get all recipes with session") {
            val getRecipesResponse = getRecipes(cookie = cookie)
            assertEquals(HttpStatus.OK, getRecipesResponse.statusCode)
            getRecipesResponse.body!!.recipes.single().id = 0
            assertEquals(
                RecipesResponse(
                    recipes = listOf(RecipeResponse(0, "name", "description", "user")),
                    addedRecipes = listOf()
                ), getRecipesResponse.body
            )
        }
    }
}
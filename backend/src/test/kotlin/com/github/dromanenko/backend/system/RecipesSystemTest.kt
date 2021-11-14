package com.github.dromanenko.backend.system

import com.github.dromanenko.backend.base.BaseSystemTest
import com.github.dromanenko.backend.response.RecipeResponse
import com.github.dromanenko.backend.response.RecipesResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class RecipesSystemTest : BaseSystemTest() {
    @Test
    fun `correct - add and get recipes`() {
        // correct - register
        val registerResponse = register("user", "password", "http://localhost/")
        assertEquals(HttpStatus.FOUND, registerResponse.statusCode)
        assertEquals("http://localhost/", registerResponse.headers.location.toString())

        // correct - add recipe with session
        val cookie = registerResponse.headers.getFirst(HttpHeaders.SET_COOKIE)!!
        val addRecipesResponse = addRecipe("name", "description", cookie)
        assertEquals(HttpStatus.OK, addRecipesResponse.statusCode)
        
        // correct - get user recipes with session
        val getMyRecipesResponse = getRecipes("user", cookie)
        assertEquals(HttpStatus.OK, getMyRecipesResponse.statusCode)
        getMyRecipesResponse.body!!.recipes.single().id = 0
        assertEquals(RecipesResponse(
            recipes = listOf(RecipeResponse(0, "name", "description", "user")),
            addedRecipes = listOf()
        ), getMyRecipesResponse.body)

        // correct - get all recipes with session
        val getRecipesResponse = getRecipes(cookie = cookie)
        assertEquals(HttpStatus.OK, getRecipesResponse.statusCode)
        getRecipesResponse.body!!.recipes.single().id = 0
        assertEquals(RecipesResponse(
            recipes = listOf(RecipeResponse(0, "name", "description", "user")),
            addedRecipes = listOf()
        ), getRecipesResponse.body)
    }
}
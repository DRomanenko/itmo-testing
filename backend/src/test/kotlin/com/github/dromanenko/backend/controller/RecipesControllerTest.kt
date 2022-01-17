package com.github.dromanenko.backend.controller

import com.github.dromanenko.backend.base.BaseControllerTest
import com.github.dromanenko.backend.base.TestModels
import com.github.dromanenko.backend.request.AddRecipeRequest
import com.github.dromanenko.backend.response.RecipesResponse
import com.github.dromanenko.backend.service.RecipesService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doThrow
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.junit4.DisplayName

@WebMvcTest(RecipesController::class)
@Epic("Backend: Controller Tests")
@Feature("Recipes Tests")
@DisplayName("Backend: Controller Recipes Tests")
class RecipesControllerTest : BaseControllerTest<RecipesService>() {
    @Test
    @Story("Add recipe")
    @DisplayName("Add recipe correct")
    fun `correct - add recipe`() {
        val recipe = AddRecipeRequest("name", "description")
        doNothing().whenever(service).addRecipe("user", recipe)
        mockMvc.post("/add-recipe") {
            session = MockHttpSession().apply { setAttribute("user", "user") }
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name":"name",
                    "description":"description"
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            content { string("") }
        }.createDocs()
        verify(service).addRecipe("user", recipe)
    }

    @Test
    @Story("Add recipe")
    @DisplayName("Add recipe correct (exception)")
    fun `correct - add recipe throw exception`() {
        val recipe = AddRecipeRequest("name", "description")
        doThrow(RuntimeException("errorText")).whenever(service).addRecipe("user", recipe)

        mockMvc.post("/add-recipe") {
            session = MockHttpSession().apply { setAttribute("user", "user") }
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name":"name",
                    "description":"description"
                }
            """.trimIndent()
        }.andExpect {
            status { isBadRequest() }
            content { string("{\"error\":\"errorText\"}") }
        }
        verify(service).addRecipe("user", recipe)
    }

    @Test
    @Story("Add recipe")
    @DisplayName("Add recipe correct (unauthorized)")
    fun `correct - add recipe returns for an unauthorised user`() {
        mockMvc.post("/add-recipe") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name":"name",
                    "description":"description"
                }
            """.trimIndent()
        }.andExpect {
            status { isUnauthorized() }
            content { string("{\"error\":\"Login to add recipe\"}") }
        }
    }

    @Test
    @Story("Get recipe")
    @DisplayName("Get recipe correct")
    fun `correct - get recipes`() {
        given(service.getRecipes(null)).willReturn(
            RecipesResponse(
                recipes = listOf(TestModels.recipeResponseModel(1, 1)),
                addedRecipes = listOf(TestModels.recipeResponseModel(2, 2))
            )
        )
        mockMvc.get("/recipes")
            .andExpect {
                status { isOk() }
                content {
                    json(
                        """
                        {
                            "recipes":[
                                {
                                    "id":1,
                                    "name":"name1",
                                    "description":"description1",
                                    "owner":"user1"
                                }
                            ],
                            "addedRecipes":[
                                {
                                    "id":2,
                                    "name":"name2",
                                    "description":"description2",
                                    "owner":"user2"
                                }
                            ]
                        }
                    """.trimIndent()
                    )
                }
            }.createDocs()
        verify(service).getRecipes(null)
    }

    @Test
    @Story("Get recipe")
    @DisplayName("Get recipe correct (exception)")
    fun `correct - get recipes throw exception`() {
        given(service.getRecipes(null)).willThrow(RuntimeException("errorText"))
        mockMvc.get("/recipes")
            .andExpect {
                status { isInternalServerError() }
                content { json("{\"error\":\"errorText\"}") }
            }
        verify(service).getRecipes(null)
    }
}
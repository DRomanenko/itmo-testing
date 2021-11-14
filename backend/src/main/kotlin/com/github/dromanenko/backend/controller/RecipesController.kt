package com.github.dromanenko.backend.controller

import com.github.dromanenko.backend.request.AddRecipeRequest
import com.github.dromanenko.backend.response.ErrorResponse
import com.github.dromanenko.backend.service.RecipesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
class RecipesController(
    @Autowired
    val recipesService: RecipesService
) {
    @GetMapping("/recipes")
    fun recipes(
        httpServletResponse: HttpServletResponse,
        @RequestParam user: String?
    ): Any {
        return try {
            return recipesService.getRecipes(user)
        } catch (e: Exception) {
            httpServletResponse.status = 500
            ErrorResponse(e.message)
        }
    }

    @PostMapping("/add-recipe")
    fun addRecipes(
        httpServletResponse: HttpServletResponse,
        httpSession: HttpSession,
        @RequestBody addRecipeRequest: AddRecipeRequest,
    ): Any {
        try {
            val sessionUser = httpSession.getAttribute("user")
            if (sessionUser == null) {
                httpServletResponse.status = 401
                return ErrorResponse("Login to add recipe")
            }
            recipesService.addRecipe(sessionUser.toString(), addRecipeRequest)
            return ""
        } catch (e: Exception) {
            httpServletResponse.status = 400
            return ErrorResponse(e.message)
        }
    }
}
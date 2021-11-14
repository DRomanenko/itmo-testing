package com.github.dromanenko.backend.service

import com.github.dromanenko.backend.entity.Recipe
import com.github.dromanenko.backend.entity.User
import com.github.dromanenko.backend.repository.RecipesRepository
import com.github.dromanenko.backend.repository.UsersRepository
import com.github.dromanenko.backend.request.AddRecipeRequest
import com.github.dromanenko.backend.response.RecipeResponse
import com.github.dromanenko.backend.response.RecipesResponse
import org.springframework.stereotype.Service

@Service
class RecipesService(
    val usersRepository: UsersRepository,
    val recipesRepository: RecipesRepository
) {
    fun addRecipe(sessionUser: String, addRecipeRequest: AddRecipeRequest) {
        val user = usersRepository.findUserByLogin(sessionUser)
        recipesRepository.save(Recipe().apply {
            name = addRecipeRequest.name
            description = addRecipeRequest.description
            owner = User().apply { id = user.id }
        })
    }

    fun getRecipes(user: String?): RecipesResponse {
        val recipes = if (user == null) recipesRepository.findAll()
        else recipesRepository.findAllByOwnerLogin(user)
        val addedRecipes = if (user == null) listOf()
        else usersRepository.findUserByLogin(user).addedRecipes
        return RecipesResponse(
            recipes = recipes.map { RecipeResponse(it.id, it.name, it.description, it.owner.login) },
            addedRecipes = addedRecipes.map { RecipeResponse(it.id, it.name, it.description, it.owner.login) }
        )
    }
}
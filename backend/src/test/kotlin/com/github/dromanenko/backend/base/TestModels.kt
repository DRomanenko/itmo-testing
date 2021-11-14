package com.github.dromanenko.backend.base

import com.github.dromanenko.backend.entity.Recipe
import com.github.dromanenko.backend.entity.User
import com.github.dromanenko.backend.response.RecipeResponse

object TestModels {
    fun user(id: Long, vararg addedRecipes: Recipe) = User().apply {
        this.id = id
        login = "user$id"
        password = "password$id"
        this.addedRecipes = addedRecipes.toMutableSet()
    }

    fun recipe(id: Long, userId: Long) = Recipe().apply {
        this.id = id
        name = "name$id"
        description = "description$id"
        owner = User().apply { login = "user$userId" }
    }
    
    fun recipeResponseModel(id: Long, userId: Long) = RecipeResponse(
        id = id,
        name = "name$id",
        description = "description$id",
        owner = "user$userId"
    )
}
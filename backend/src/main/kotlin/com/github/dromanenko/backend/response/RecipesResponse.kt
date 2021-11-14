package com.github.dromanenko.backend.response

data class RecipesResponse(
    val recipes: List<RecipeResponse>,
    val addedRecipes: List<RecipeResponse>
)
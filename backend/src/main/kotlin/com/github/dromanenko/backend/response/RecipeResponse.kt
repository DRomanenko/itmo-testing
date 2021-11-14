package com.github.dromanenko.backend.response

data class RecipeResponse(
    var id: Long,
    val name: String,
    val description: String,
    val owner: String
)

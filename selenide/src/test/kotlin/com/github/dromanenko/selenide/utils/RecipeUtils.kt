package com.github.dromanenko.selenide.utils

import com.github.dromanenko.selenide.model.Recipe
import com.github.dromanenko.selenide.model.User
import kotlin.random.Random

object RecipeUtils {
    fun createRandomRecipe(user: User) =
        Random.nextInt().let {
            Recipe(
                "name$it",
                "description$it",
                user.login
            )
        }
}
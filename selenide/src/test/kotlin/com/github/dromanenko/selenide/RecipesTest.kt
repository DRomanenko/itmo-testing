package com.github.dromanenko.selenide

import com.codeborne.selenide.Selenide
import com.github.dromanenko.selenide.utils.RecipeUtils
import com.github.dromanenko.selenide.utils.Steps
import com.github.dromanenko.selenide.utils.UserUtils
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("Selenide Tests")
@Feature("Recipes Tests")
@DisplayName("Selenide Recipes Tests (E2E)")
class RecipesTest : BaseSelenideTest() {
    @Test
    @Story("Register and add recipe")
    fun `Register and add recipe`() {
        val user = UserUtils.createRandomUser()
        val placesList = RecipeUtils.createRandomRecipe(user)

        Selenide.open("/register")
        Steps.sendCredentials(user)

        Selenide.open("/add-recipe")
        Steps.addRecipe(placesList)
    }
}
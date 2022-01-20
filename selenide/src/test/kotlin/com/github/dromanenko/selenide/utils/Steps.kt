package com.github.dromanenko.selenide.utils

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selectors
import com.codeborne.selenide.Selenide
import com.github.dromanenko.selenide.model.Recipe
import com.github.dromanenko.selenide.model.User

object Steps {
    fun sendCredentials(user: User) {
        Selenide.`$`("#login").shouldHave(Condition.attribute("placeholder", "Login"))
        Selenide.`$`("#password").shouldHave(Condition.attribute("placeholder", "Password"))

        Selenide.`$`("#login").sendKeys(user.login)
        Selenide.`$`("#password").sendKeys(user.password)
        Selenide.`$`(".login-button").click()
    }

    fun logout() {
        Selenide.`$`(Selectors.byAttribute("href", "#"))
            .shouldHave(Condition.text("Sign out"))
        Selenide.`$`(Selectors.byAttribute("href", "#")).click()
    }

    fun checkHelloText(user: User) =
        Selenide.`$`("h1").shouldHave(Condition.text("Hello, ${user.login}! These are your favourite recipes!"))

    fun addRecipe(recipe: Recipe) {
        Selenide.`$`("#name").shouldHave(Condition.attribute("placeholder", "Name"))
        Selenide.`$`("#description").shouldHave(Condition.attribute("placeholder", "Description"))

        Selenide.`$`("#name").sendKeys(recipe.name)
        Selenide.`$`("#description").sendKeys(recipe.description)
        Selenide.`$`(".login-button").click()
    }
}
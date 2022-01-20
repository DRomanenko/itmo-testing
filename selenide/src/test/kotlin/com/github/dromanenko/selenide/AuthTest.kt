package com.github.dromanenko.selenide

import com.codeborne.selenide.Selenide
import com.github.dromanenko.selenide.utils.Steps
import com.github.dromanenko.selenide.utils.UserUtils
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("Selenide Tests")
@Feature("Auth Tests")
@DisplayName("Selenide Auth Tests (E2E)")
class AuthTest : BaseSelenideTest() {
    @Test
    @Story("Register and login")
    fun `Register and login`() {
        val user = UserUtils.createRandomUser()

        Selenide.open("/register")
        Steps.sendCredentials(user)
        Steps.checkHelloText(user)
        Steps.logout()

        Selenide.open("/login")
        Steps.sendCredentials(user)
        Steps.checkHelloText(user)
        Steps.logout()
    }
}
package com.github.dromanenko.backend.system

import com.github.dromanenko.backend.base.AllureHelper
import com.github.dromanenko.backend.base.BaseSystemTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.Description
import io.qameta.allure.junit4.DisplayName

@Epic("Backend: System Tests")
@Feature("Authorization Tests")
@DisplayName("Backend: System Authorization Tests")
class AuthSystemTest : BaseSystemTest() {
    @Test
    @Story("User must register/login/logout only with correct data")
    @DisplayName("All auth operations")
    @Description("Register, register with same credentials, login and logout")
    fun `correct - some auth operations`() {
        AllureHelper.step("Correct - register") {
            val registerResponse1 = register("login", "password", "http://localhost/")
            assertEquals(HttpStatus.FOUND, registerResponse1.statusCode)
            assertEquals("http://localhost/", registerResponse1.headers.location.toString())
        }

        AllureHelper.step("Correct - register with same login failed") {
            val registerResponse2 = register("login", "password2", "http://localhost/")
            assertEquals(HttpStatus.FOUND, registerResponse2.statusCode)
            assertEquals("http://localhost/register", registerResponse2.headers.location.toString())
        }

        val cookie = AllureHelper.step("Correct - login") {
            val loginResponse = login("login", "password", "http://localhost/")
            assertEquals(HttpStatus.FOUND, loginResponse.statusCode)
            assertEquals("http://localhost/", loginResponse.headers.location.toString())
            loginResponse.headers.getFirst(HttpHeaders.SET_COOKIE)!!
        }

        AllureHelper.step("Correct - logout") {
            val logout = unlogin(cookie)
            assertEquals(HttpStatus.OK, logout.statusCode)
        }

        AllureHelper.step("Correct - current user after logout") {
            val currentUserResponse = currentUser(cookie)
            assertEquals(HttpStatus.OK, currentUserResponse.statusCode)
            assertEquals("{\"user\":null}", currentUserResponse.body)
        }
    }
}
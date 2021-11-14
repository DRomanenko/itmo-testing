package com.github.dromanenko.backend.controller

import com.github.dromanenko.backend.base.BaseControllerTest
import com.github.dromanenko.backend.entity.User
import com.github.dromanenko.backend.request.AuthRequest
import com.github.dromanenko.backend.service.AuthService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(AuthController::class)
class AuthControllerTest : BaseControllerTest<AuthService>() {
    @Test
    fun `correct - current-user returns for an unauthorised user`() {
        mockMvc.get("/current-user").andExpect {
            status { isOk() }
            content { json("{\"user\":null}") }
        }
    }

    @Test
    fun `correct - current-user returns for an authorised user`() {
        mockMvc.get("/current-user") {
            session = MockHttpSession().apply { setAttribute("user", "password") }
        }.andExpect {
            status { isOk() }
            content { json("{\"user\":\"password\"}") }
        }.createDocs()
    }

    @Test
    fun `correct - register`() {
        val authRequest = AuthRequest("login", "password", "redirectTo")
        given(service.register(authRequest)).willReturn(User().apply {
            login = "login"
            password = "hashPassword"
        })
        mockMvc.post("/register") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            param("login", "login")
            param("password", "password")
            param("redirectTo", "redirectTo")
        }.andExpect {
            status {
                is3xxRedirection()
                redirectedUrl("redirectTo")
            }
            content { string("") }
            request { sessionAttribute("user", "login") }
        }.createDocs()
    }

    @Test
    fun `incorrect - register`() {
        val authRequest = AuthRequest("login", "password", "redirectTo/")
        given(service.register(authRequest)).willThrow(RuntimeException())
        mockMvc.post("/register") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            param("login", "login")
            param("password", "password")
            param("redirectTo", "redirectTo/")
        }.andExpect {
            status {
                is3xxRedirection()
                redirectedUrl("redirectTo/register")
            }
            content { string("") }
            request { sessionAttribute("user", null) }
        }
    }

    @Test
    fun `correct - login`() {
        val authRequest = AuthRequest("login", "password", "redirectTo")
        given(service.login(authRequest)).willReturn(User().apply {
            login = "login"
            password = "hashPassword"
        })
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            param("login", "login")
            param("password", "password")
            param("redirectTo", "redirectTo")
        }.andExpect {
            status {
                is3xxRedirection()
                redirectedUrl("redirectTo")
            }
            content { string("") }
            request { sessionAttribute("user", "login") }
        }.createDocs()
    }

    @Test
    fun `incorrect - login`() {
        val authRequest = AuthRequest("login", "password", "redirectTo/")
        given(service.login(authRequest)).willThrow(RuntimeException())
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            param("login", "login")
            param("password", "password")
            param("redirectTo", "redirectTo/")
        }.andExpect {
            status {
                is3xxRedirection()
                redirectedUrl("redirectTo/login")
            }
            content { string("") }
            request { sessionAttribute("user", null) }
        }
    }
}
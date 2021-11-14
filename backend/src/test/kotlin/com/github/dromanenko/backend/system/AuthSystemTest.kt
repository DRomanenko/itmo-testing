package com.github.dromanenko.backend.system

import com.github.dromanenko.backend.base.BaseSystemTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthSystemTest : BaseSystemTest() {
    @Test
    fun `correct - some auth operations`() {
        // correct - register
        val registerResponse1 = register("login", "password", "http://localhost/")
        assertEquals(HttpStatus.FOUND, registerResponse1.statusCode)
        assertEquals("http://localhost/", registerResponse1.headers.location.toString())

        // correct - register with same login failed
        val registerResponse2 = register("login", "password2", "http://localhost/")
        assertEquals(HttpStatus.FOUND, registerResponse2.statusCode)
        assertEquals("http://localhost/register", registerResponse2.headers.location.toString())

        // correct - login
        val loginResponse = login("login", "password", "http://localhost/")
        assertEquals(HttpStatus.FOUND, loginResponse.statusCode)
        assertEquals("http://localhost/", loginResponse.headers.location.toString())

        val cookie = loginResponse.headers.getFirst(HttpHeaders.SET_COOKIE)!!

        // correct - logout
        val logout = unlogin(cookie)
        assertEquals(HttpStatus.OK, logout.statusCode)

        // correct - current user after logout
        val currentUserResponse = currentUser(cookie)
        assertEquals(HttpStatus.OK, currentUserResponse.statusCode)
        assertEquals("{\"user\":null}", currentUserResponse.body)
    }
}
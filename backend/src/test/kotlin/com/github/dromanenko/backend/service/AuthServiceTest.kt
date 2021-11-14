package com.github.dromanenko.backend.service

import com.github.dromanenko.backend.base.BaseServiceTest
import com.github.dromanenko.backend.entity.User
import com.github.dromanenko.backend.request.AuthRequest
import com.github.dromanenko.backend.utils.Sha256
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthServiceTest : BaseServiceTest() {
    private val authService = AuthService(mockUsersRepo)

    @Test
    fun `correct - register`() {
        val hash = Sha256.hash("password")
        lateinit var user: User
        `when`(mockUsersRepo.save(any())).then { user = it.arguments[0] as User; return@then user }
        val newUser = authService.register(AuthRequest("login", "password", ""))
        assertEquals("login", newUser.login)
        assertEquals(hash, newUser.password)
        verify(mockUsersRepo).save(user)
    }

    @Test
    fun `correct - login`() {
        val hash = Sha256.hash("password")
        whenever(mockUsersRepo.findUserByLoginAndPassword("login", hash)).thenReturn(User().apply {
            id = 1
            login = "login"
            password = hash
        })
        val newUser = authService.login(AuthRequest("login", "password", ""))
        assertEquals("login", newUser.login)
        assertEquals(hash, newUser.password)
        verify(mockUsersRepo).findUserByLoginAndPassword("login", hash)
    }
}
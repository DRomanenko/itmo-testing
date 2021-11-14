package com.github.dromanenko.backend.controller

import com.github.dromanenko.backend.request.AuthRequest
import com.github.dromanenko.backend.response.UserResponse
import com.github.dromanenko.backend.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
class AuthController(
    @Autowired
    val authService: AuthService
) {
    companion object {
        const val USER_SESSION = "user"
    }

    @GetMapping("/current-user")
    fun currentUser(httpSession: HttpSession): UserResponse {
        return UserResponse(httpSession.getAttribute(USER_SESSION) as? String)
    }

    @PostMapping("/register")
    fun register(
        httpServletResponse: HttpServletResponse,
        httpSession: HttpSession,
        authRequest: AuthRequest
    ) {
        try {
            val user = authService.register(authRequest)
            httpSession.setAttribute(USER_SESSION, user.login)
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequest.redirectTo)
        } catch (e: Exception) {
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequest.redirectTo + "register")
        }
    }

    @PostMapping("/login")
    fun login(
        httpServletResponse: HttpServletResponse,
        httpSession: HttpSession,
        authRequest: AuthRequest
    ) {
        try {
            val user = authService.login(authRequest)
            httpSession.setAttribute(USER_SESSION, user.login)
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequest.redirectTo)
        } catch (e: Exception) {
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequest.redirectTo + "login")
        }
    }

    @PostMapping("/unlogin")
    fun unlogin(httpSession: HttpSession) = httpSession.setAttribute(USER_SESSION, null)
}
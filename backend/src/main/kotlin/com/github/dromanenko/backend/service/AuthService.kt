package com.github.dromanenko.backend.service

import com.github.dromanenko.backend.entity.User
import com.github.dromanenko.backend.repository.UsersRepository
import com.github.dromanenko.backend.request.AuthRequest
import com.github.dromanenko.backend.utils.Sha256
import org.springframework.stereotype.Service

@Service
class AuthService(
    val usersRepository: UsersRepository
) {
    fun register(authRequest: AuthRequest) =
        usersRepository.save(User().apply {
            login = authRequest.login
            password = Sha256.hash(authRequest.password)
        })

    fun login(authRequest: AuthRequest) =
        usersRepository.findUserByLoginAndPassword(
            authRequest.login,
            Sha256.hash(authRequest.password)
        )
}
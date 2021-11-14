package com.github.dromanenko.backend.repository

import com.github.dromanenko.backend.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : CrudRepository<User, Long> {
    fun findUserByLogin(login: String): User
    fun findUserByLoginAndPassword(login: String, password: String): User
}
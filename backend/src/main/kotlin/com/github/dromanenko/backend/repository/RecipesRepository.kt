package com.github.dromanenko.backend.repository

import com.github.dromanenko.backend.entity.Recipe
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipesRepository : CrudRepository<Recipe, Long> {
    fun findAllByOwnerLogin(login: String): List<Recipe>
}
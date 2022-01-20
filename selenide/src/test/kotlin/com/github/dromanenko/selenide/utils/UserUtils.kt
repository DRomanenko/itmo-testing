package com.github.dromanenko.selenide.utils

import com.github.dromanenko.selenide.model.User
import kotlin.random.Random

object UserUtils {
    fun createRandomUser() =
        Random.nextInt().let {
            User(
                "login$it",
                "password$it"
            )
        }
}
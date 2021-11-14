package com.github.dromanenko.backend.request

data class AuthRequest(
    var login: String,
    var password: String,
    var redirectTo: String
)
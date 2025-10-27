package com.github.Zambrinn.KotlinAPI.dto

import io.jsonwebtoken.security.Password
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 30)
    val username: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String
)

data class LoginRequest(
    @field:NotBlank
    val username: String,

    @field:NotBlank
    val password: String
)

data class AuthenticationResponse(
    val token: String
)
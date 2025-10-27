package com.github.Zambrinn.KotlinAPI.auth

import com.github.Zambrinn.KotlinAPI.dto.AuthenticationResponse
import com.github.Zambrinn.KotlinAPI.dto.LoginRequest
import com.github.Zambrinn.KotlinAPI.dto.RegisterRequest
import com.github.Zambrinn.KotlinAPI.model.User
import com.github.Zambrinn.KotlinAPI.model.UserRole
import com.github.Zambrinn.KotlinAPI.repository.UserRepository
import com.github.Zambrinn.KotlinAPI.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        val existingUser = userRepository.findBy_username(request.username)
        if (existingUser != null) {
            // TODO: CREATE A CUSTOM EXCEPTION
            throw IllegalStateException("Username already taken")
        }

        val newUser = User(
            _username = request.username,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            role = UserRole.USER
        )

        val savedUser = userRepository.save(newUser)
        val jwtToken = jwtService.generateToken(savedUser)
        return AuthenticationResponse(token = jwtToken)
    }

    fun login(request: LoginRequest): AuthenticationResponse {
        authManager
            .authenticate(
                UsernamePasswordAuthenticationToken(
                    request.username,
                    request.password
                )
            )

        val user = userRepository.findBy_username(request.username)
            ?: throw UsernameNotFoundException("User not found after successful login") // exceção que nunca deve acontecer

        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(token = jwtToken)
    }
}
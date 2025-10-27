package com.github.Zambrinn.KotlinAPI.repository

import com.github.Zambrinn.KotlinAPI.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findBy_username(_username: String): User?
}
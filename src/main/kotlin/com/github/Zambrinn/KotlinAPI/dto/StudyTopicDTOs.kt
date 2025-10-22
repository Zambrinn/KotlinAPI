package com.github.Zambrinn.KotlinAPI.dto

import com.github.Zambrinn.KotlinAPI.model.StudyStatus
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class StudyTopicRequest(
    @field:NotBlank(message = "Your study topic must have a name")
    val name: String,

    @field:NotBlank(message = "Your study topic must have a category")
    val category: String
)

data class StudyTopicResponse(
    val id: Long,
    val name: String,
    val category: String,
    val status: StudyStatus,
    val creationDate: LocalDate
)



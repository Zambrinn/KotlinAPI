package com.github.Zambrinn.KotlinAPI.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
data class StudyTopic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String,
    var category: String,
    @Enumerated(EnumType.STRING)
    var status: StudyStatus = StudyStatus.PENDENT,
    val creationDate: LocalDate = LocalDate.now()
)

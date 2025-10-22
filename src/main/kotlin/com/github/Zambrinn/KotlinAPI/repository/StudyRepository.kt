package com.github.Zambrinn.KotlinAPI.repository

import com.github.Zambrinn.KotlinAPI.model.StudyTopic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyRepository : JpaRepository<StudyTopic, Long> {

}
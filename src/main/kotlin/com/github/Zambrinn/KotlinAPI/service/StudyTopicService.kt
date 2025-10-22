package com.github.Zambrinn.KotlinAPI.service

import com.github.Zambrinn.KotlinAPI.dto.StudyTopicRequest
import com.github.Zambrinn.KotlinAPI.dto.StudyTopicResponse
import com.github.Zambrinn.KotlinAPI.model.StudyTopic
import org.springframework.stereotype.Service
import com.github.Zambrinn.KotlinAPI.repository.StudyRepository;

@Service
class StudyTopicService(
    private val repository: StudyRepository
) {
    fun createStudyTopic(request: StudyTopicRequest): StudyTopicResponse {
        val entityToSave = StudyTopic(
            name = request.name,
            category = request.category
        )

        val savedEntity = repository.save(entityToSave);

        return savedEntity.toResponse()

    }

    private fun StudyTopic.toResponse(): StudyTopicResponse {
        return StudyTopicResponse(
            id = this.id!!,
            name = this.name,
            category = this.category,
            status = this.status,
            creationDate = this.creationDate
        )
    }
}


package com.github.Zambrinn.KotlinAPI.service

import com.github.Zambrinn.KotlinAPI.dto.StudyTopicRequest
import com.github.Zambrinn.KotlinAPI.dto.StudyTopicResponse
import com.github.Zambrinn.KotlinAPI.model.StudyTopic
import org.springframework.stereotype.Service
import com.github.Zambrinn.KotlinAPI.repository.StudyRepository;
import org.springframework.data.repository.findByIdOrNull

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

    fun getAllTopics(): List<StudyTopicResponse> {
        val allEntities: List<StudyTopic> = repository.findAll()

        return allEntities.map { it.toResponse() }
    }

    fun getTopicById(id: Long): StudyTopicResponse? {
        val entityFound = repository.findByIdOrNull(id);
        return entityFound?.toResponse();
    }

    fun updateTopic(id: Long, request: StudyTopicRequest): StudyTopicResponse? {
        return repository.findByIdOrNull(id)?.let { existingTopic ->
            val updatedTopic = existingTopic.copy(
                name = request.name,
                category = request.category
            )
            repository.save(updatedTopic).toResponse()
        }
    }
    fun deleteTopic(id: Long): Boolean {
        if (repository.existsById(id)) {
            repository.deleteById(id)
            return true
        }
        return false
    }


    private fun StudyTopic.toResponse(): StudyTopicResponse {
        return StudyTopicResponse(
            id = this.id!!, // non nullable object
            name = this.name,
            category = this.category,
            status = this.status,
            creationDate = this.creationDate
        )
    }
}


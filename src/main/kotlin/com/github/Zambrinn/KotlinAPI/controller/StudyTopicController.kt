package com.github.Zambrinn.KotlinAPI.controller

import com.github.Zambrinn.KotlinAPI.dto.StudyTopicRequest
import com.github.Zambrinn.KotlinAPI.dto.StudyTopicResponse
import com.github.Zambrinn.KotlinAPI.model.StudyTopic
import com.github.Zambrinn.KotlinAPI.service.StudyTopicService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController()
@RequestMapping("/api/v1/study-topics")
class StudyTopicController (
    private val service: StudyTopicService
) {
    @PostMapping
    fun createStudyTopic(@Valid @RequestBody request: StudyTopicRequest): ResponseEntity<StudyTopicResponse> {
        val topicResponse = service.createStudyTopic(request)
        val location = URI.create("/api/v1/study-topics/${topicResponse.id}")

        return ResponseEntity.created(location).body(topicResponse)
    }
}
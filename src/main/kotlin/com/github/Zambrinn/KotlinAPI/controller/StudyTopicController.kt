package com.github.Zambrinn.KotlinAPI.controller

import com.github.Zambrinn.KotlinAPI.dto.StudyTopicRequest
import com.github.Zambrinn.KotlinAPI.dto.StudyTopicResponse
import com.github.Zambrinn.KotlinAPI.model.StudyTopic
import com.github.Zambrinn.KotlinAPI.service.StudyTopicService
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
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

    @GetMapping
    fun listAllTopics(): List<StudyTopicResponse> {
        return service.getAllTopics();
    }

    @GetMapping("/{id}")
    fun getTopicById(@PathVariable id: Long): ResponseEntity<StudyTopicResponse> {
        val foundTopic = service.getTopicById(id)

        return foundTopic?.let { topic -> ResponseEntity.ok(topic) }
            ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun updateTopic(@PathVariable id: Long, @Valid @RequestBody request: StudyTopicRequest): ResponseEntity<*>? {
        return service.updateTopic(id, request)?.let {
            updatedTopic ->
            ResponseEntity.ok(updatedTopic) }
                ?: ResponseEntity.notFound().build<Void>()
        }

    @DeleteMapping("/{id}")
    fun deleteTopic(@PathVariable id: Long): ResponseEntity<*> {
            return if (service.deleteTopic(id)) {
                ResponseEntity.noContent().build<Void>()
            } else {
                ResponseEntity.noContent().build<Void>()
            }
        }
    }
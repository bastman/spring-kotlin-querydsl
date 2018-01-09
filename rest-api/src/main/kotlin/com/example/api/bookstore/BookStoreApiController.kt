package com.example.api.bookstore

import com.example.api.bookstore.domain.repo.AuthorRepoService
import mu.KLogging
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
class BookStoreApiController(private val authorRepo: AuthorRepoService) {

    @GetMapping("/api/bookstore/author")
    fun authorsFindAll() =
            authorRepo.findAll()
                    .results
                    .toList()
                    .map { it.toAuthorDto() }

    @GetMapping("/api/bookstore/author/{id}")
    fun authorsGetOne(@PathVariable id: UUID) =
            authorRepo.requireOneById(id)
                    .toAuthorDto()

    @PutMapping("/api/bookstore/author")
    fun authorsCreateOne(@RequestBody req: AuthorCreateRequest) =
            req.toAuthorRecord()
                    .let { authorRepo.insert(it) }
                    .also { logger.info { "Updated Record: $it" } }
                    .toAuthorDto()

    @PostMapping("/api/bookstore/author/{id}")
    fun authorsUpdateOne(@PathVariable id: UUID, @RequestBody req: AuthorUpdateRequest): AuthorDto
            = authorRepo.requireOneById(id)
            .copy(modifiedAt = Instant.now(), name = req.name)
            .let { authorRepo.update(it) }
            .also { logger.info { "Updated Record: $it" } }
            .toAuthorDto()


    companion object : KLogging()
}
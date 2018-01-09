package com.example.api.bookstore

import com.example.api.bookstore.domain.repo.AuthorRepoService
import com.example.api.bookstore.domain.repo.BookRepoService
import mu.KLogging
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
class BookStoreApiController(
        private val authorRepo: AuthorRepoService,
        private val bookRepo: BookRepoService
) {

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


    @GetMapping("/api/bookstore/book/{id}")
    fun booksGetOne(@PathVariable id: UUID) =
            bookRepo.requireOneById(id)

    @PutMapping("/api/bookstore/book")
    fun booksCreateOne(@RequestBody req: BookCreateRequest) =
            req.toBookRecord()
                    .let { bookRepo.insert(it) }
                    .also { logger.info { "Updated Record: $it" } }


    @PostMapping("/api/bookstore/book/{id}")
    fun booksUpdateOne(@PathVariable id: UUID, @RequestBody req: BookUpdateRequest)
            = bookRepo.requireOneById(id)
            .copy(modifiedAt = Instant.now(), title = req.title, status = req.status, price = req.price)
            .let { bookRepo.update(it) }
            .also { logger.info { "Updated Record: $it" } }
    /*
          @GetMapping("/api/bookstore/book")
      fun booksFindAll() = bookRepo.findAllBooksJoinAuthor().map { it.toBookDto() }

       */


    companion object : KLogging()
}
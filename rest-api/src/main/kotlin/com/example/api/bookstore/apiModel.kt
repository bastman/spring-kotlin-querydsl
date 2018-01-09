package com.example.api.bookstore

import com.example.api.bookstore.domain.db.Author
import com.example.api.bookstore.domain.db.Book
import com.example.api.bookstore.domain.db.BookStatus
import com.example.api.bookstore.domain.repo.BookRecordJoinAuthorRecord
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class AuthorCreateRequest(val name: String)
data class AuthorUpdateRequest(val name: String)
data class BookCreateRequest(val authorId: UUID, val title: String, val status: BookStatus, val price: BigDecimal)
data class BookUpdateRequest(val title: String, val status: BookStatus, val price: BigDecimal)

data class AuthorDto(val id: UUID, val createdAt: Instant, val modifiedAt: Instant, val name: String)
data class BookDto(
        val id: UUID,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val title: String,
        val status: BookStatus,
        val price: BigDecimal,
        val author: AuthorDto
)

fun Author.toAuthorDto() = AuthorDto(id = id, createdAt = createdAt, modifiedAt = modifiedAt, name = name)

fun AuthorCreateRequest.toAuthorRecord(): Author {
    val now = Instant.now()
    return Author(
            id = UUID.randomUUID(),
            createdAt = now,
            modifiedAt = now,
            version = 0,
            name = name
    )
}

fun BookCreateRequest.toBookRecord(): Book {
    val now = Instant.now()
    return Book(
            id = UUID.randomUUID(),
            createdAt = now,
            modifiedAt = now,
            version = 0,
            authorId = authorId,
            title = title,
            status = status,
            price = price
    )
}

fun BookRecordJoinAuthorRecord.toBookDto() =
        BookDto(
                id = bookRecord.id,
                createdAt = bookRecord.createdAt,
                modifiedAt = bookRecord.modifiedAt,
                title = bookRecord.title,
                status = bookRecord.status,
                price = bookRecord.price,
                author = authorRecord.toAuthorDto()
        )


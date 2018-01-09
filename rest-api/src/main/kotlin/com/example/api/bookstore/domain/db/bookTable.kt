package com.example.api.bookstore.domain.db

import com.example.util.jpa.JpaTypes
import mu.KLogging
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book")
data class Book(
        @Id
        @Type(type = JpaTypes.UUID)
        val id: UUID,
        @Version
        val version: Int = -1,
        @Column(name = "created_at", nullable = false)
        var createdAt: Instant,
        @Column(name = "updated_at", nullable = false)
        var modifiedAt: Instant,

        @Column(name = "author_id", nullable = false)
        val authorId: UUID,
        @Column(name = "title", nullable = false)
        val title: String,
        @Column(name = "status", nullable = false)
        val status: BookStatus,
        @Column(name = "price", nullable = false)
        val price: BigDecimal
) {
    companion object : KLogging()
}

enum class BookStatus { NEW, PUBLISHED; }
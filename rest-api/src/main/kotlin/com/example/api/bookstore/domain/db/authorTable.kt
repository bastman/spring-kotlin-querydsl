package com.example.api.bookstore.domain.db

import com.example.util.jpa.JpaTypes
import mu.KLogging
import org.hibernate.annotations.Type
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "author")
data class Author(
        @Id
        @Type(type = JpaTypes.UUID)
        val id: UUID,
        @Version
        val version: Int = -1,
        @Column(name = "created_at", nullable = false)
        var createdAt: Instant,
        @Column(name = "updated_at", nullable = false)
        var modifiedAt: Instant,

        @Column(name = "name", nullable = false)
        val name: String
) {
    companion object : KLogging()
}
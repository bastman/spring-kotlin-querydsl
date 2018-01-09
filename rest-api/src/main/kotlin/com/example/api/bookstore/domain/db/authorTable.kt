package com.example.api.bookstore.domain.db

import com.example.util.jpa.JpaTypes
import mu.KLogging
import org.hibernate.annotations.Type
import org.hibernate.search.annotations.Analyze
import org.hibernate.search.annotations.Field
import org.hibernate.search.annotations.Index
import org.hibernate.search.annotations.Indexed
import org.hibernate.search.annotations.Store
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "author")
@Indexed
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
        @Field(index=Index.YES, analyze=Analyze.YES, store= Store.NO)
        val name: String
) {
    companion object : KLogging()
}
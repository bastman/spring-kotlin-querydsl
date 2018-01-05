package com.example.api.tweeter.domain.entities

import com.example.util.jpa.JpaTypes
import mu.KLogging
import org.hibernate.annotations.Type
import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Version

@Entity
//@EntityListeners(JpaAuthorListener::class)
data class Tweet(
        @Id
        @Type(type = JpaTypes.UUID)
        val id: UUID,
        @Version
        val version: Int = -1,
        @Column(name = "created_at", nullable = false)
        var createdAt: Instant,
        @Column(name = "updated_at", nullable = false)
        var modifiedAt: Instant,

        @Column(name = "message", nullable = false)
        val message: String,
        @Column(name = "comment", nullable = true)
        val comment: String?
) {


    companion object : KLogging()
}

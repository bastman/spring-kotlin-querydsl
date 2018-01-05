package com.example.api.tweeter

import com.example.api.tweeter.domain.TweetRepoService
import com.example.api.tweeter.domain.entities.Tweet
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
class TweeterApiController(
        private val repo: TweetRepoService
) {

    @GetMapping("/api/tweeter")
    fun findAll() = repo.findAll().results.map { it.toDto() }

    @GetMapping("/api/tweeter/{id}")
    fun getOne(@PathVariable id: UUID) = repo.requireOneById(id).toDto()


    @PutMapping("/api/tweeter")
    fun createOne(@RequestBody req: CreateTweetRequest): TweetDto {
        val tweet = req.toTweet()

        return repo
                .insert(tweet)
                .toDto()
    }

    @PostMapping("/api/tweeter/{id}")
    fun updateOne(@PathVariable id: UUID, @RequestBody req: CreateTweetRequest): TweetDto {
        val tweet = repo
                .requireOneById(id)
                .copy(
                        modifiedAt = Instant.now(),
                        message = req.message,
                        comment = req.comment
                )

        return repo.update(tweet).toDto()
    }
}

data class CreateTweetRequest(val message: String, val comment: String?)

data class TweetDto(
        val id: UUID,
        val version: Int,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val message: String,
        val comment: String?
)

private fun Tweet.toDto() = TweetDto(
        id = id,
        version = version,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
        message = message,
        comment = comment
)

private fun CreateTweetRequest.toTweet(): Tweet {
    val now = Instant.now()

    return Tweet(id = UUID.randomUUID(), version = 0, createdAt = now, modifiedAt = now, message = message, comment = comment)
}
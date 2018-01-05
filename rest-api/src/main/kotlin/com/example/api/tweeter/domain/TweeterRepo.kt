package com.example.api.tweeter.domain

import com.example.api.tweeter.domain.entities.QTweet
import com.example.api.tweeter.domain.entities.Tweet
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional


@Repository
interface TweetJpaRepository : JpaRepository<Tweet, UUID> {}

@Component
class TweetRepoService(
        private val jpaQueryFactory: JPAQueryFactory,
        private val jpaRepo: TweetJpaRepository
) : QueryDslRepositorySupport(Tweet::class.java) {

    fun getOneById(id: UUID): Tweet? = jpaRepo.getOne(id)

    fun requireOneById(id: UUID): Tweet
            = getOneById(id) ?: throw EntityNotFoundException("TweetRecord NOT FOUND ! (id=$id)")

    @Transactional
    fun findAll() =
            from(QTweet.tweet)
                    .fetchAll().fetchResults()

    fun insert(tweet: Tweet): Tweet {
        return jpaRepo.save(tweet)
    }

    fun update(tweet: Tweet): Tweet {
        return jpaRepo.save(tweet)
    }


}
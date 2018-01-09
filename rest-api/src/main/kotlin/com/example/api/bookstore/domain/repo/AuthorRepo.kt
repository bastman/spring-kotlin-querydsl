package com.example.api.bookstore.domain.repo

import com.example.api.bookstore.domain.db.Author
import com.example.api.bookstore.domain.db.QAuthor
import com.example.api.common.EntityAlreadyExistException
import com.example.api.common.EntityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface AuthorJpaRepository : JpaRepository<Author, UUID> {}

@Component
@Transactional  // should be moved to RequestHandler
class AuthorRepoService(
        private val jpaRepo: AuthorJpaRepository
) : QueryDslRepositorySupport(Author::class.java) {
    fun getOneById(id: UUID): Author? = jpaRepo.findOne(id)

    fun requireOneById(id: UUID): Author
            = getOneById(id) ?: throw EntityNotFoundException("AuthorRecord NOT FOUND ! (id=$id)")

    fun existsById(id: UUID) = jpaRepo.exists(id)

    fun requireExistsById(id: UUID) {
        if (!existsById(id)) throw EntityNotFoundException("AuthorRecord NOT FOUND ! (id=$id)")
    }

    fun requireDoesNotExistById(id: UUID) {
        if (existsById(id)) throw EntityAlreadyExistException("AuthorRecord ALREADY EXIST ! (id=$id)")
    }


    fun insert(author: Author): Author =
            author.also { requireDoesNotExistById(it.id) }
                    .let { jpaRepo.save(it) }

    fun update(author: Author): Author =
            author.also { requireExistsById(it.id) }
                    .let { jpaRepo.save(it) }

    fun findAll() =
            from(QAuthor.author)
                    .fetchAll().fetchResults()
}
package com.example.api.bookstore.domain.repo

import com.example.api.bookstore.domain.db.Book
import com.example.api.bookstore.domain.db.QBook
import com.example.api.common.EntityAlreadyExistException
import com.example.api.common.EntityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface BookJpaRepository : JpaRepository<Book, UUID> {}

@Component
@Transactional  // should be moved to RequestHandler
class BookRepoService(
        private val jpaRepo: BookJpaRepository
) : QueryDslRepositorySupport(Book::class.java) {
    fun getOneById(id: UUID): Book? = jpaRepo.findOne(id)

    fun requireOneById(id: UUID): Book
            = getOneById(id) ?: throw EntityNotFoundException("$CRUD_RECORD_NAME NOT FOUND ! (id=$id)")

    fun existsById(id: UUID) = jpaRepo.exists(id)

    fun requireExistsById(id: UUID) {
        if (!existsById(id)) throw EntityNotFoundException("$CRUD_RECORD_NAME NOT FOUND ! (id=$id)")
    }

    fun requireDoesNotExistById(id: UUID) {
        if (existsById(id)) throw EntityAlreadyExistException("$CRUD_RECORD_NAME ALREADY EXIST ! (id=$id)")
    }


    fun insert(book: Book): Book =
            book.also { requireDoesNotExistById(it.id) }
                    .let { jpaRepo.save(it) }

    fun update(book: Book): Book =
            book.also { requireExistsById(it.id) }
                    .let { jpaRepo.save(it) }

    fun findAll() =
            from(Q_RECORD)
                    .fetchAll().fetchResults()

    companion object {
        val CRUD_RECORD_NAME = "BookRecord"
        val Q_RECORD = QBook.book
    }
}
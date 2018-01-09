package com.example.api.bookstore.domain.repo

import com.example.api.bookstore.domain.db.Author
import com.example.api.bookstore.domain.db.Book
import com.example.api.bookstore.domain.db.QTables
import com.example.api.common.EntityAlreadyExistException
import com.example.api.common.EntityNotFoundException
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
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
        private val jpaRepo: BookJpaRepository,
        private val authorRepoService: AuthorRepoService,
        private val jpaQueryFactory: JPAQueryFactory
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
                    .also { authorRepoService.requireExistsById(it.authorId) }
                    .let { jpaRepo.save(it) }

    fun update(book: Book): Book =
            book.also { requireExistsById(it.id) }
                    .also { authorRepoService.requireExistsById(it.authorId) }
                    .let { jpaRepo.save(it) }

    fun findAll() =
            from(Q_CRUD_TABLE)
                    .fetchAll().fetchResults()

    fun findAllBooksJoinAuthor(): List<BookRecordJoinAuthorRecord> {
        val authorTable = QTables.AUTHOR
        val bookTable = QTables.BOOK
        val proj = Projections.constructor(
                BookRecordJoinAuthorRecord::class.java,
                bookTable,
                authorTable
        )

        return jpaQueryFactory.select(proj)
                .from(authorTable)
                .innerJoin(bookTable)
                .on(bookTable.authorId.eq(authorTable.id))
                .fetchResults()
                .results.toList()
    }

    fun findAllBooksJoinAuthorAsSummary(): List<BookWithAuthorSummary> {
        val authorTable = QTables.AUTHOR
        val bookTable = QTables.BOOK
        val proj = Projections.constructor(
                BookWithAuthorSummary::class.java,
                authorTable.id, authorTable.name,
                bookTable.id, bookTable.title
        )

        return jpaQueryFactory.select(proj)
                .from(authorTable)
                .innerJoin(bookTable)
                .on(bookTable.authorId.eq(authorTable.id))
                .fetchResults()
                .results.toList()
    }

    companion object {
        val CRUD_RECORD_NAME = "BookRecord"
        val Q_CRUD_TABLE = QTables.AUTHOR
    }
}

data class BookWithAuthorSummary(val authorId: UUID, val authorName: String, val bookId: UUID, val bookTitle: String)
data class BookRecordJoinAuthorRecord(val bookRecord: Book, val authorRecord: Author)
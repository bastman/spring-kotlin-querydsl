package com.example.api.bookstore.domain.es


import com.example.api.bookstore.domain.db.Author
import com.example.api.bookstore.domain.db.QTables
import org.hibernate.search.jpa.FullTextQuery
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional


import org.springframework.stereotype.Repository

/**
 * Search methods for the entity User using Hibernate search.
 * The Transactional annotation ensure that transactions will be opened and
 * closed at the beginning and at the end of each method.
 *
 * @author netgloo
 */
@Repository
@Transactional
class AuthorSearch(
        @PersistenceContext
        private val entityManager: EntityManager
) {


    /**
     * A basic search for the entity User. The search is done by exact match per
     * keywords on fields name, city and email.
     *
     * @param text The query text.
     */
    //@Transactional
    fun search(text: String): List<Author> {

        // get the full text entity manager
        val fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager)

        // create the query using Hibernate Search query DSL
        val queryBuilder = fullTextEntityManager.searchFactory
                .buildQueryBuilder().forEntity(Author::class.java).get()

        // a very basic query by keywords
        val query = queryBuilder
                .keyword()
                //Query fullTextQuery = tweetQb.keyword().onField(Tweet_.message.getName()).matching(“Message updated”).createQuery();
                .onFields(Author::name.name)
                .matching(text)
                .createQuery()

        // wrap Lucene query in an Hibernate Query object
        val jpaQuery:FullTextQuery = fullTextEntityManager.createFullTextQuery(query, Author::class.java)

        // execute search and return results (sorted by relevance as default)
//@SuppressWarnings("unchecked")
        return jpaQuery.resultList.toList().map { it as Author }
    }


}
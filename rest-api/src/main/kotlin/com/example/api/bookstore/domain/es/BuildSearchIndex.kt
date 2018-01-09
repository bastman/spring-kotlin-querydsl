package com.example.api.bookstore.domain.es

import mu.KLogging
import org.hibernate.search.jpa.Search.getFullTextEntityManager
import org.hibernate.search.jpa.FullTextEntityManager
import org.hibernate.search.jpa.Search
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Component
class BuildSearchIndex(
        @PersistenceContext
        private val entityManager:EntityManager
) : ApplicationListener<ApplicationReadyEvent> {
    @Transactional
    override fun onApplicationEvent(event: ApplicationReadyEvent?) {
        try {
            val fullTextEntityManager = Search.getFullTextEntityManager(entityManager)
            logger.info { "ES: Indexer Start" }
            fullTextEntityManager.createIndexer().startAndWait()
            logger.info { "ES: Indexer Done" }
        } catch (e: Exception) {
            logger.error {
                "ES: Indexer Failed! reason=$e"
            }
        }
    }

    /**
     * Create an initial Lucene index for the data already present in the
     * database.
     * This method is called when Spring's startup.
     */
    /*
    fun onApplicationEvent(event: ApplicationReadyEvent) {
        try {
            val fullTextEntityManager = Search.getFullTextEntityManager(entityManager)
            fullTextEntityManager.createIndexer().startAndWait()
        } catch (e: InterruptedException) {
            println(
                    "An error occurred trying to build the serach index: " + e.toString())
        }

        return
    }
    */

    companion object: KLogging()

}
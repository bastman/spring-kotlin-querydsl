package com.example.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.inject.Provider
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Configuration
class QueryDsl(@PersistenceContext private val em: EntityManager) {

    val jpaQueryFactory: JPAQueryFactory
        @Bean
        get() {
            val provider = Provider<EntityManager> { em }
            return JPAQueryFactory(provider)
        }
}
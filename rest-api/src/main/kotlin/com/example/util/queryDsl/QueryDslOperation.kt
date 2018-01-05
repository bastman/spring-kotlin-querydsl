package com.example.util.queryDsl

import com.fasterxml.jackson.annotation.JsonValue

object QueryDslOperation {
    const val ASC = "asc"
    const val DESC = "desc"
    const val LIKE = "like"
    const val EQ = "eq"
    const val GOE = "goe"
    const val LOE = "loe"
}

enum class QueryDslPredicateCombiner(val text:String) {
    ALL_OF("allOf"),
    ANY_OF("anyOf")
    ;

    @JsonValue
    fun jsonValue():String = text
}




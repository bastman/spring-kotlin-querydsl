# spring-kotlin-querydsl
playground for spring-boot, kotlin , querydsl (with jpa, hibernate)

## build

```
    $ make -C rest-api app.build

```

## run local db (docker)

```
    $ make -C rest-api db.local.up

```
## the magic behind the query-dsl-kapt-codegenerator
- https://github.com/querydsl/querydsl/tree/master/querydsl-jpa


## This example project is based on ...
- https://github.com/making/spring-boot-db-samples

## Note: QueryDSL works fine with hibernate, but does not require it.

- http://querydsl.com
- https://github.com/querydsl/querydsl/blob/master/querydsl-examples/querydsl-example-sql-spring/src/main/java/com/querydsl/example/config/JdbcConfiguration.java


## Whats wrong with orm, jpa, hibernate and in-memory h2-db these days ?

There is no silver bullet. 
It's born in a world of single-instance big fat application servers.
It hardly fits into a modern world of:

- functional programming: e.g. immutable threadsafe pojos / data classes 
- CQRS and eventsourcing
- horizontal scaling of polyglot microservices

Make up your mind ...

- How hibernate ruined Monica's career: https://www.toptal.com/java/how-hibernate-ruined-my-career
- Why do I hate hibernate: https://de.slideshare.net/alimenkou/why-do-i-hate-hibernate-12998784
- ORM is an antipattern: http://seldo.com/weblog/2011/08/11/orm_is_an_antipattern
- Opinionated JPA: https://leanpub.com/opinionatedjpa/read
- Lightweight ORM, do it yourself: https://blog.philipphauer.de/do-it-yourself-orm-alternative-hibernate-drawbacks/
- Don't use H2 db for testing, use docker: https://blog.philipphauer.de/dont-use-in-memory-databases-tests-h2/


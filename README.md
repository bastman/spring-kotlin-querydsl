# spring-kotlin-querydsl
playground for spring-boot, kotlin , querydsl (with jpa, hibernate)

## build

```
    # $ make -C rest-api help
    $ make -C rest-api app.build

```

## run local db (docker)

```
    # $ make -C rest-api help
    $ make -C rest-api db.local.up

```

## things to read ...
- https://github.com/making/spring-boot-db-samples
- https://leanpub.com/opinionatedjpa/read

## note: get rid of hibernate?

- see https://github.com/querydsl/querydsl/blob/master/querydsl-examples/querydsl-example-sql-spring/src/main/java/com/querydsl/example/config/JdbcConfiguration.java

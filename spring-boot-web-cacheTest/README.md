# spring-boot-web-cacheTest

### Description
Cache coding test

### Database
MySQL 5.7.19

### NoSQL Database
Redis 2.8.4

### Structure

1.  Please find the logic for fetching data from Redis and Mysql in class: RedisCache and MysqlLoadingFunction, they implement interface Cache and LoadingFunction
2.  Please find the unit tests for the core methods in class: SpringBootCacheTestApplicationTests
3.  Please find the related configuration in application.properties
4.  Please install above version MySQL and Redis and create database: cachetest in MySQL before start the project
5.  The project has also provided the restful API to fetch data from cache.
    [http://[SERVER IP]:[PORT]/cacheTest/emp?id=[SPECIFIC ID]](http://[SERVER IP]:[PORT]/cacheTest/emp?id=[SPECIFIC ID])

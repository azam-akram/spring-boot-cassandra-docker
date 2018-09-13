package com.demo.spring.boot.cassandra.docker.repository;

import com.demo.spring.boot.cassandra.docker.entity.BookStoreEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookStoreRepository extends CassandraRepository<BookStoreEntity, Integer> {
}

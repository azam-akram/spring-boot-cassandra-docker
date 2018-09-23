package com.demo.spring.boot.cassandra.docker.repository;

import com.demo.spring.boot.cassandra.docker.entity.BookStoreEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface BookStoreRepository extends CassandraRepository<BookStoreEntity, UUID> {
    Optional<BookStoreEntity> findByUuid(UUID uuid);
}

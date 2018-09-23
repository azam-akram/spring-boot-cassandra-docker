package com.demo.spring.boot.cassandra.docker.service;

import com.demo.spring.boot.cassandra.docker.entity.BookStoreEntity;

import java.util.List;
import java.util.UUID;

public interface BookStoreService {

    List<BookStoreEntity> getAllBookStores();

    BookStoreEntity getBookStoreById(UUID id);

    BookStoreEntity saveBookStore(BookStoreEntity bookStoreEntity);

    BookStoreEntity updateBookStore(BookStoreEntity bookStoreEntity);

    void deleteBookStore(UUID id);

}
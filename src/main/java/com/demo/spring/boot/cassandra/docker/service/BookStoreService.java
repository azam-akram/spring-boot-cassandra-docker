package com.demo.spring.boot.cassandra.docker.service;

import com.demo.spring.boot.cassandra.docker.entity.BookStoreEntity;

import java.util.List;
import java.util.UUID;

public interface BookStoreService {

    List<BookStoreEntity> getAllBookStores();

    BookStoreEntity getBookStoreById(Integer id);

    BookStoreEntity saveBookStore(BookStoreEntity errorDetailEntity);

    BookStoreEntity updateBookStore(BookStoreEntity errorDetailEntity);

    void deleteBookStore(Integer id);

}
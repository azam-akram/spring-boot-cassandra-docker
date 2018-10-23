package com.demo.spring.boot.cassandra.docker.service;

import com.demo.spring.boot.cassandra.docker.model.entity.BookStoreEntity;
import com.demo.spring.boot.cassandra.docker.model.input.BookStoreInput;
import com.demo.spring.boot.cassandra.docker.model.output.BookStoreOutput;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookStoreService {

    List<BookStoreOutput> getAllBookStores();

    BookStoreOutput getBookStoreById(UUID id);

    BookStoreOutput saveBookStore(BookStoreInput input);

    BookStoreOutput updateBookStore(BookStoreInput input);

    void deleteBookStore(UUID id);

}
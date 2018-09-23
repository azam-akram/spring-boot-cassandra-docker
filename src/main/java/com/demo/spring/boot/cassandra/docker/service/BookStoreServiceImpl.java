package com.demo.spring.boot.cassandra.docker.service;

import com.demo.spring.boot.cassandra.docker.entity.BookStoreEntity;
import com.demo.spring.boot.cassandra.docker.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class BookStoreServiceImpl implements BookStoreService {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    public BookStoreServiceImpl(BookStoreRepository bookStoreRepository) {
        this.bookStoreRepository = bookStoreRepository;
    }

    @Override
    public List<BookStoreEntity> getAllBookStores() {
        return bookStoreRepository.findAll();
    }

    @Override
    public BookStoreEntity getBookStoreById(UUID id) {
        Optional<BookStoreEntity> bookStoreEntity = bookStoreRepository.findByUuid(id);
        if(bookStoreEntity.get() != null) {
            return bookStoreEntity.get();
        } else {
            return null;
        }
    }

    @Override
    public BookStoreEntity saveBookStore(BookStoreEntity bookStoreEntity) {
        return bookStoreRepository.save(bookStoreEntity);
    }

    @Override
    public BookStoreEntity updateBookStore(BookStoreEntity bookStoreEntity) {
        return bookStoreRepository.save(getModifiedBookStoreEntity());
    }

    @Override
    public void deleteBookStore(UUID id) {
        bookStoreRepository.deleteById(id);
    }

    private BookStoreEntity getBookStoreEntity() {
        BookStoreEntity entity = BookStoreEntity.builder()
                .id(UUID.randomUUID())
                .title("book 1")
                .writer("writer 1")
                .build();
        return entity;
    }

    private BookStoreEntity getModifiedBookStoreEntity() {
        BookStoreEntity entity = BookStoreEntity.builder()
                .id(UUID.randomUUID())
                .title("modified book title")
                .writer("writer 1")
                .build();
        return entity;
    }
}

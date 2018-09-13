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
    public BookStoreEntity getBookStoreById(Integer id) {
        Optional<BookStoreEntity> errorDetailEntity = bookStoreRepository.findById(id);
        if(errorDetailEntity.get() != null) {
            return errorDetailEntity.get();
        } else {
            return null;
        }
    }

    @Override
    public BookStoreEntity saveBookStore(BookStoreEntity errorDetailEntity) {
        return bookStoreRepository.save(errorDetailEntity);
    }

    @Override
    public BookStoreEntity updateBookStore(BookStoreEntity errorDetailEntity) {
        return bookStoreRepository.save(getModifiedBookStoreEntity());
    }

    @Override
    public void deleteBookStore(Integer id) {
        bookStoreRepository.deleteById(id);
    }

    // TODO: Just for experiments un till we have full controller ready, Must be deleted from here
    private BookStoreEntity getBookStoreEntity() {
        BookStoreEntity entity = BookStoreEntity.builder()
                .id(1)
                .title("book 1")
                .writer("writer 1")
                .build();
        return entity;
    }

    // TODO: Just for experiments un till we have full controller ready, Must be deleted from here
    private BookStoreEntity getModifiedBookStoreEntity() {
        BookStoreEntity entity = BookStoreEntity.builder()
                .id(1)
                .title("modified book title")
                .writer("writer 1")
                .build();
        return entity;
    }
}

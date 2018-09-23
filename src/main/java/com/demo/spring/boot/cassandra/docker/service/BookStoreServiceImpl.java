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
    public Optional<BookStoreEntity> getBookStoreById(UUID id) {
        return bookStoreRepository.findByUuid(id);
    }

    @Override
    public BookStoreEntity saveBookStore(BookStoreEntity bookStoreEntity) {
        return bookStoreRepository.save(bookStoreEntity);
    }

    @Override
    public BookStoreEntity updateBookStore(BookStoreEntity bookStoreEntity) {
        return bookStoreRepository.save(bookStoreEntity);
    }

    @Override
    public void deleteBookStore(UUID uuid) {
        bookStoreRepository.deleteById(uuid);
    }

}

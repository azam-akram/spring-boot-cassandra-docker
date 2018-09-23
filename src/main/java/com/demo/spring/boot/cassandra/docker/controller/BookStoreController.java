package com.demo.spring.boot.cassandra.docker.controller;

import com.demo.spring.boot.cassandra.docker.service.BookStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping(path = "/books")
public class BookStoreController {

    @Autowired
    BookStoreService bookStoreService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAllBooks() {
        log.trace("Reading all books");
        try {
            return ok(bookStoreService.getAllBookStores());
        } catch (Exception e) {
            log.error("Catch exception when reading books records.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

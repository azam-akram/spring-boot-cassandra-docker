package com.demo.spring.boot.cassandra.docker.controller;

import com.demo.spring.boot.cassandra.docker.entity.BookStoreEntity;
import com.demo.spring.boot.cassandra.docker.service.BookStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping(path = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getBookById(@PathVariable(value = "uuid") final UUID uuid) {
        log.trace("Reading book information by Id");
        Optional<BookStoreEntity> optionalBookStoreEntity = bookStoreService.getBookStoreById(uuid);
        if (optionalBookStoreEntity.isPresent()) {
            return new ResponseEntity<>(bookStoreService.getBookStoreById(uuid), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveBook(@Valid @RequestBody final BookStoreEntity bookStoreEntity) {
        log.trace("Saving book");
        return new ResponseEntity<>(bookStoreService.saveBookStore(bookStoreEntity), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateBook(@Valid @RequestBody final BookStoreEntity bookStoreEntity) {
        log.trace("Update book");
        return new ResponseEntity<>(bookStoreService.updateBookStore(bookStoreEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "uuid") final UUID uuid) {
        log.trace("Update book");
        bookStoreService.deleteBookStore(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

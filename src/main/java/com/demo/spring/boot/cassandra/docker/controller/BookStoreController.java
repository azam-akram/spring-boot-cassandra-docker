package com.demo.spring.boot.cassandra.docker.controller;

import com.demo.spring.boot.cassandra.docker.service.BookStoreService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

//TODO: Controller is not done yet, there is a different ticket for this, should be done as part of that.

@Slf4j
@RestController
@RequestMapping(path = "/books")
public class BookStoreController {

    private static Logger logger = LoggerFactory.getLogger(BookStoreController.class);

    @Autowired
    BookStoreService bookStoreService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getErrorDetails() {
        logger.info("Reading all error details");
        try {
            return ok(bookStoreService.getAllBookStores());
        } catch (Exception e) {
            logger.error("Catch exception when reading error details.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

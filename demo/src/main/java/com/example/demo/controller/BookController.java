package com.example.demo.controller;


import com.example.demo.data.BookRequest;
import com.example.demo.data.BookResponse;
import com.example.demo.data.WebResponse;
import com.example.demo.model.BookEntity;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookEntity> getAllBooks(@RequestHeader(value = "TRACE-CODE") String traceCode,
                                        @RequestHeader(value = "X-Request-Source") String requestSource) {
        logger.info("------------ Start Services Get All Book ------------");
        logger.info("Request TRACE-CODE = "+ traceCode);
        logger.info("X-Request-Source = "+ requestSource);
        logger.info("------------ End Services Get All Book ------------");
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable Long id,@RequestHeader(value = "TRACE-CODE") String traceCode,
                                  @RequestHeader(value = "X-Request-Source") String requestSource) {
        logger.info("------------ Start Services Get Book ID------------");
        logger.info("Request TRACE-CODE = "+ traceCode);
        logger.info("X-Request-Source = "+ requestSource);
        logger.info("------------ End Services Get Book ID------------");
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookResponse createBook(@Valid @RequestBody BookRequest book, @RequestHeader(value = "TRACE-CODE") String traceCode,
                                   @RequestHeader(value = "X-Request-Source") String requestSource) {
        logger.info("------------ Start Services Create Book ------------");
        logger.info("Request TRACE-CODE = "+ traceCode);
        logger.info("X-Request-Source = "+ requestSource);
        BookResponse data =bookService.addBook(book);
        logger.info("------------ End Services Create Book ------------");
        return data;
    }

//    @PutMapping("/{id}")
//    public BookEntity updateBook(@PathVariable Long id, @RequestBody BookEntity book) {
//        return bookService.update(id, book);
//    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id, @RequestHeader(value = "TRACE-CODE") String traceCode,
                             @RequestHeader(value = "X-Request-Source") String requestSource) {
        logger.info("------------ Start Services Delete Book ------------");
        logger.info("Request TRACE-CODE = "+ traceCode);
        logger.info("X-Request-Source = "+ requestSource);
        bookService.deleteBookById(id);
        logger.info("------------ End Services Delete Book ------------");
        return "Buku dengan ID " + id + " berhasil dihapus.";
    }

}

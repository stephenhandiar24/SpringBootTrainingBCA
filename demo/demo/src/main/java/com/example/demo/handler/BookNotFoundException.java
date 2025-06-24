package com.example.demo.handler;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("BookEntity not found with id: " + id);
    }
    public BookNotFoundException(String title) {
        super("BookEntity not found with title: " + title);
    }

}

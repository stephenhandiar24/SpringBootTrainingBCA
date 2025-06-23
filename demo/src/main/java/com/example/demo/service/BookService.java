package com.example.demo.service;

import com.example.demo.data.BookRequest;
import com.example.demo.data.BookResponse;
import com.example.demo.handler.BookNotFoundException;
import com.example.demo.model.BookEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private List<BookEntity> books = new ArrayList<>();
    private Long currentId = 1L;
    public List<BookEntity> getAllBooks() {
        return books;
    }
    public BookResponse addBook(BookRequest bookReq) {
        BookEntity bookVar = new BookEntity(currentId++, bookReq.getTitle(), bookReq.getAuthor());
        books.add(bookVar);
        BookResponse bookResponse =new BookResponse(bookVar.getId(),bookVar.getTitle(), bookVar.getAuthor());
        return bookResponse;
    }
    public BookEntity getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
    }
    public void deleteBookById(Long id) {
        BookEntity book = getBookById(id);
        books.remove(book);
    }
}

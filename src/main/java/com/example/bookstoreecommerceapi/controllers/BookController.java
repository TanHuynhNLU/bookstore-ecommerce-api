package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllBooks(){
        ResponseObject responseObject = bookService.getAllBooks();
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addNewBook(@Valid  @RequestBody Book newBook) throws BookAlreadyExistsException {
        ResponseObject responseObject = bookService.addNewBook(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }
}

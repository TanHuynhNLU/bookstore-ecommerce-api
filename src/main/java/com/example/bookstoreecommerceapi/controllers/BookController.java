package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
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
    public ResponseEntity<ResponseObject> getAllBooks() {
        ResponseObject responseObject = bookService.getAllBooks();
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBookById(@PathVariable long id) throws BookNotFoundException {
        ResponseObject responseObject = bookService.getBookById(id);
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> getAllBooksPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PaginationResponse paginationResponse = bookService.getAllBooksPaginationAndSorting(page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<PaginationResponse> searchBooks(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PaginationResponse paginationResponse = bookService.searchBooks(q, page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/check-name/{name}")
    public ResponseEntity<ResponseObject> checkName(@PathVariable String name) {
        ResponseObject responseObject = bookService.isNameExists(name);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addNewBook(@Valid @RequestBody Book newBook) throws BookAlreadyExistsException {
        ResponseObject responseObject = bookService.addNewBook(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateBook(@PathVariable long id, @Valid @RequestBody Book book) throws BookNotFoundException {
        ResponseObject responseObject = bookService.updateBook(id, book);
        return ResponseEntity.ok(responseObject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBook(@PathVariable long id) throws BookNotFoundException {
        ResponseObject responseObject = bookService.deleteBook(id);
        return ResponseEntity.ok(responseObject);
    }
}

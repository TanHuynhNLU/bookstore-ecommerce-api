package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
@Tag(name = "Book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getAllBooks() {
        ResponseObject responseObject = bookService.getAllBooks();
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getBookById(@PathVariable long id) throws BookNotFoundException {
        ResponseObject responseObject = bookService.getBookById(id);
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<PaginationResponse> getAllBooksPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PaginationResponse paginationResponse = bookService.getAllBooksPaginationAndSorting(page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> checkName(@PathVariable String name) {
        ResponseObject responseObject = bookService.isNameExists(name);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> addNewBook(@Valid @RequestBody Book newBook) throws BookAlreadyExistsException {
        ResponseObject responseObject = bookService.addNewBook(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> updateBook(@PathVariable long id, @Valid @RequestBody Book book) throws BookNotFoundException {
        ResponseObject responseObject = bookService.updateBook(id, book);
        return ResponseEntity.ok(responseObject);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> deleteBook(@PathVariable long id) throws BookNotFoundException {
        ResponseObject responseObject = bookService.deleteBook(id);
        return ResponseEntity.ok(responseObject);
    }
}

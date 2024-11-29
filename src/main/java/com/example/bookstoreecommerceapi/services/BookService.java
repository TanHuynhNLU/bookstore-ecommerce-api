package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.models.Book;

import java.util.List;

public interface BookService {
    ResponseObject getAllBooks();

    PaginationResponse getBooks(List<String> genres, List<String> publishers, List<String> priceRanges, int page, int size, String sort);

    ResponseObject getBookById(long id) throws BookNotFoundException;


    PaginationResponse getAllBooksPaginationAndSorting(int page, int size, String sort);

    PaginationResponse searchBooks(String q, int page, int size, String sort);

    ResponseObject isNameExists(String name);

    ResponseObject addNewBook(Book newBook) throws BookAlreadyExistsException;

    ResponseObject updateBook(long id, Book book) throws BookNotFoundException;

    ResponseObject deleteBook(long id) throws BookNotFoundException;

}

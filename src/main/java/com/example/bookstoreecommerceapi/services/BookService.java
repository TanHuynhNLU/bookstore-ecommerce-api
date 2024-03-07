package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.Book;

public interface BookService {
    ResponseObject getAllBooks();

    ResponseObject addNewBook(Book newBook) throws BookAlreadyExistsException;
}

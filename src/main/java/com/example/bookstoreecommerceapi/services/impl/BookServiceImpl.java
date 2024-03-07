package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.repositories.BookRepository;
import com.example.bookstoreecommerceapi.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseObject getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return new ResponseObject(HttpStatus.OK, "Thành công", books);
    }
}

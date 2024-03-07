package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.repositories.BookRepository;
import com.example.bookstoreecommerceapi.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseObject getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return new ResponseObject(HttpStatus.OK, "Thành công", books);
    }

    @Override
    public ResponseObject addNewBook(Book newBook) throws BookAlreadyExistsException {
        Optional<Book> bookOptional = bookRepository.findByName(newBook.getName());
        if (bookOptional.isPresent()) throw new BookAlreadyExistsException("Tên sách đã tồn tại");
        bookRepository.save(newBook);
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED, "Thêm sách thành công", newBook);
        return responseObject;
    }
}

package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
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
        Book savedbook = bookRepository.save(newBook);
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED, "Thêm sách thành công", savedbook);
        return responseObject;
    }

    @Override
    public ResponseObject updateBook(long id, Book book) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) throw new BookNotFoundException("Sách không tồn tại");
        Book bookDB = optionalBook.get();
        bookDB.setName(book.getName());
        bookDB.setAuthor(book.getAuthor());
        bookDB.setGenre(book.getGenre());
        bookDB.setPrice(book.getPrice());
        bookDB.setStock(book.getStock());
        bookDB.setDescription(book.getDescription());
        bookDB.setImage(book.getImage());
        bookDB.setPublished(book.getPublished());
        bookDB.setNumberOfPage(book.getNumberOfPage());
        bookDB.setPublisher(book.getPublisher());

        Book savedBook = bookRepository.save(bookDB);

        return new ResponseObject(HttpStatus.OK, "Cập nhật sách thành công", savedBook);
    }

    @Override
    public ResponseObject deleteBook(long id) throws BookNotFoundException {
        boolean isExists = bookRepository.existsById(id);
        if(!isExists) throw new BookNotFoundException("Sách không tồn tại");
        bookRepository.deleteById(id);
        return new ResponseObject(HttpStatus.OK,"Xóa sách thành công",null);
    }
}

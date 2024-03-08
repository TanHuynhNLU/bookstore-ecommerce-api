package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.repositories.BookRepository;
import com.example.bookstoreecommerceapi.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    private Book book;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .name("Nhà giả kim")
                .author("Paulo Coelho")
                .price(70_000)
                .genre("Tiểu thuyết")
                .build();
    }

    @Test
    @DisplayName("Junit test for getAllBooks method")
    public void whenGetAllBooks_thenReturnList(){
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
       List<Book> mockBooks = List.of(book,book1);
       when(bookRepository.findAll()).thenReturn(mockBooks);
        ResponseObject responseObject = bookService.getAllBooks();
        List<Book> actualBooks = (List<Book>) responseObject.getData();
        assertNotNull(actualBooks);
        assertEquals(2,actualBooks.size());
    }

    @Test
    @DisplayName("Junit test for addNewBook method")
    public void whenAddNewBook_thenReturnBookObject() throws BookAlreadyExistsException {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        when(bookRepository.save(book1)).thenReturn(book1);
        ResponseObject responseObject = bookService.addNewBook(book1);
        Book actualBook = (Book) responseObject.getData();
        assertNotNull(actualBook);
        assertEquals("Chuyện con mèo dạy hải âu bay",actualBook.getName());
    }

    @Test
    @DisplayName("Junit test for updateBook method")
    public void whenUpdateBook_thenReturnBookObject() throws  BookNotFoundException {
        Book book = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();

        Book updatedBook = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(80_000)
                .genre("Tiểu thuyết")
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        ResponseObject responseObject = bookService.updateBook(1L, updatedBook);
        Book actualBook = (Book) responseObject.getData();
        assertNotNull(actualBook);
        assertEquals(80_000,actualBook.getPrice());
    }
}
package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
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
                .price(70000)
                .genre("Tiểu thuyết")
                .build();
    }

    @Test
    @DisplayName("Junit test for getAllBooks method")
    public void whenGetAllBooks_thenReturnList(){
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40000)
                .genre("Tiểu thuyết")
                .build();
       List<Book> mockBooks = List.of(book,book1);
       when(bookRepository.findAll()).thenReturn(mockBooks);
        ResponseObject responseObject = bookService.getAllBooks();
        List<Book> actualBooks = (List<Book>) responseObject.getData();
        assertNotNull(actualBooks);
        assertEquals(2,actualBooks.size());
    }
}
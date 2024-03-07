package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
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
    public void getAllBooks() throws Exception {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        List<Book> mockBooks = List.of(book, book1);
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK,"Thành công",mockBooks);
        when(bookService.getAllBooks()).thenReturn(responseObject);
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void addNewBook() throws Exception {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED,"Thêm sách thành công",book1);
        when(bookService.addNewBook(book1)).thenReturn(responseObject);
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\":\"Chuyện con mèo dạy hải âu bay\",\n" +
                                "    \"author\": \"Luis Sepúlveda\",\n" +
                                "    \"price\": 40000,\n" +
                                "    \"genre\":\"Tiểu thuyết\"\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void addNewBookExists() throws Exception {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        when(bookService.addNewBook(book1)).thenThrow(new BookAlreadyExistsException("Tên sách đã tồn tại"));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\":\"Chuyện con mèo dạy hải âu bay\",\n" +
                                "    \"author\": \"Luis Sepúlveda\",\n" +
                                "    \"price\": 40000,\n" +
                                "    \"genre\":\"Tiểu thuyết\"\n" +
                                "}"))
                .andExpect(status().isConflict());
    }
}
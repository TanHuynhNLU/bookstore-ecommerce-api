package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
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
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Thành công", mockBooks);
        when(bookService.getAllBooks()).thenReturn(responseObject);
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void getBookById() throws Exception {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Thành công", book1);
        when(bookService.getBookById(1L)).thenReturn(responseObject);
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Chuyện con mèo dạy hải âu bay"));
    }

    @Test
    public void getAllBooksPaginationAndSorting() throws Exception {
        Book book1 = Book.builder()
                .name("Sach 1")
                .author("Tac gia 1")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        Book book2 = Book.builder()
                .name("Sach 2")
                .author("Tac gia 2")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        Book book3 = Book.builder()
                .name("Sach 3")
                .author("Tac gia 3")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        List<Book> mockBooks = List.of(book1, book2, book3);
        PaginationResponse paginationResponse = new PaginationResponse(3, mockBooks, 1, 0);
        when(bookService.getAllBooksPaginationAndSorting(0, 3, "id")).thenReturn(paginationResponse);
        mockMvc.perform(get("/api/books/pagination?page=0&size=3&sort=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(3));
    }

    @Test
    public void searchBooks() throws Exception {
        Book book1 = Book.builder()
                .name("Sách 1")
                .author("Tác giả 1")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        Book book2 = Book.builder()
                .name("Sách 2")
                .author("Tác giả 2")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        Book book3 = Book.builder()
                .name("Sách 3")
                .author("Tác giả 3")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        List<Book> mockBooks = List.of(book1, book2, book3);
        PaginationResponse paginationResponse = new PaginationResponse(3, mockBooks, 1, 0);
        when(bookService.searchBooks("sach", 0, 3, "id")).thenReturn(paginationResponse);
        mockMvc.perform(get("/api/books/search?q=sach&page=0&size=3&sort=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(3));
    }

    @Test
    public void checkName() throws Exception {
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Tên sách đã tồn tại", null);
        when(bookService.isNameExists("Nhà giả kim")).thenReturn(responseObject);
        mockMvc.perform(get("/api/books/check-name/Nhà giả kim"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Tên sách đã tồn tại"));
    }

    @Test
    public void addNewBook() throws Exception {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED, "Thêm sách thành công", book1);
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

    @Test
    public void updateBook() throws Exception {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Cập nhật sách thành công", book1);
        when(bookService.updateBook(1l, book1)).thenReturn(responseObject);
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\":\"Chuyện con mèo dạy hải âu bay\",\n" +
                                "    \"author\": \"Luis Sepúlveda\",\n" +
                                "    \"price\": 40000,\n" +
                                "    \"genre\":\"Tiểu thuyết\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBookNotFound() throws Exception {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        when(bookService.updateBook(1l, book1)).thenThrow(new BookNotFoundException("Sách không tồn tại"));
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\":\"Chuyện con mèo dạy hải âu bay\",\n" +
                                "    \"author\": \"Luis Sepúlveda\",\n" +
                                "    \"price\": 40000,\n" +
                                "    \"genre\":\"Tiểu thuyết\"\n" +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBook() throws Exception {
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Xóa sách thành công", null);
        when(bookService.deleteBook(1L)).thenReturn(responseObject);
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }
}
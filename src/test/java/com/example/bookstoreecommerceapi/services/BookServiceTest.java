package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
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
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

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
    @DisplayName("JUnit test for getAllBooks method")
    public void whenGetAllBooks_thenReturnList() {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        List<Book> mockBooks = List.of(book, book1);
        when(bookRepository.findAll()).thenReturn(mockBooks);
        ResponseObject responseObject = bookService.getAllBooks();
        List<Book> actualBooks = (List<Book>) responseObject.getData();
        assertNotNull(actualBooks);
        assertEquals(2, actualBooks.size());
    }

    @Test
    @DisplayName("JUnit test for getAllBooksPaginationAndSorting method")
    public void whenGetAllBooksPaginationAndSorting_thenReturnList() {
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
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
        Page<Book> mockBookPage = new PageImpl<>(mockBooks);
        when(bookRepository.findAll(pageable)).thenReturn(mockBookPage);
        PaginationResponse paginationResponse = bookService.getAllBooksPaginationAndSorting(0, 2, "id");

        assertNotNull(paginationResponse);
        assertEquals(3, paginationResponse.getTotalItems());
    }

    @Test
    @DisplayName("JUnit test for searchBooks method")
    public void whenSearchBooks_thenReturnList() {
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
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
        Page<Book> mockBookPage = new PageImpl<>(mockBooks);
        when(bookRepository.searchBooks("sach", 0, pageable)).thenReturn(mockBookPage);
        PaginationResponse paginationResponse = bookService.searchBooks("sach", 0, 2, "id");

        assertNotNull(paginationResponse);
        assertEquals(3, paginationResponse.getTotalItems());
    }

    @Test
    @DisplayName("JUnit test for isNameExists method")
    public void whenNameExists_thenReturnHttpStatusOk() throws BookNotFoundException {
        when(bookRepository.existsByName("Nhà giả kim")).thenReturn(true);
        ResponseObject responseObject = bookService.isNameExists("Nhà giả kim");
        assertEquals(HttpStatus.OK, responseObject.getStatus());
    }

    @Test
    @DisplayName("JUnit test for addNewBook method")
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
        assertEquals("Chuyện con mèo dạy hải âu bay", actualBook.getName());
    }

    @Test
    @DisplayName("JUnit test for updateBook method")
    public void whenUpdateBook_thenReturnBookObject() throws BookNotFoundException {
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
        assertEquals(80_000, actualBook.getPrice());
    }

    @Test
    @DisplayName("JUnit test for deleteBook method")
    public void whenDeleteBook_thenNothing() throws BookNotFoundException {
        long bookId = book.getId();
        when(bookRepository.existsById(bookId)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(bookId);
        bookService.deleteBook(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
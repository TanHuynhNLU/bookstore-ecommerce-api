package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Book book = Book.builder()
                .name("Nhà giả kim")
                .author("Paulo Coelho")
                .price(70_000)
                .genre("Tiểu thuyết")
                .build();
        testEntityManager.persist(book);
    }

    @Test
    @DisplayName("JUnit test for findAll method")
    public void whenFindAll_ThenReturnList() {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        testEntityManager.persist(book1);

        assertNotNull(bookRepository.findAll());
        assertEquals(2, bookRepository.findAll().size());
    }

    @Test
    @DisplayName("JUnit test for findByName method")
    public void whenFindByName_ThenReturnBookObject() {
        Optional<Book> book = bookRepository.findByName("Nhà giả kim");

        assertTrue(book.isPresent());
    }

    @Test
    @DisplayName("JUnit test for save method")
    public void whenSave_ThenReturnBookObject() {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        Book actualBook = bookRepository.save(book1);

        assertEquals("Chuyện con mèo dạy hải âu bay", actualBook.getName());
    }

    @Test
    @DisplayName("JUnit test for update book")
    @DirtiesContext
    public void whenUpdateBook_thenReturnBookObject() {
        Book book = Book.builder()
                .name("Sach01")
                .author("Tac Gia 1")
                .price(70_000)
                .genre("Tiểu thuyết")
                .build();
         bookRepository.save(book);

         Book savedBook = bookRepository.findByName("Sach01").get();
         assertNotNull(savedBook);
         savedBook.setPrice(50_000);
         bookRepository.save(savedBook);

         Book updatedBook = bookRepository.findByName("Sach01").get();
         assertNotNull(updatedBook);
        assertEquals(50_000, updatedBook.getPrice());
    }

    @Test
    @DisplayName("JUnit test for deleteById method")
    public void whenDeleteBook_thenNothing() {
        Book book = Book.builder()
                .name("Sach01")
                .author("Tac Gia 1")
                .price(70_000)
                .genre("Tiểu thuyết")
                .build();
        Book savedBook = bookRepository.save(book);

        bookRepository.deleteById(book.getId());
        assertFalse(bookRepository.existsById(savedBook.getId()));
    }
}
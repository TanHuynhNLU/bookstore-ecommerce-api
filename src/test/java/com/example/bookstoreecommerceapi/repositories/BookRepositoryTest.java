package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @DisplayName("JUnit test for findById method")
    public void whenFindById_ThenReturnBookObject() {
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        Book mockedBook = testEntityManager.persist(book1);
        Optional<Book> bookOptional = bookRepository.findById(mockedBook.getId());
        assertEquals("Chuyện con mèo dạy hải âu bay", bookOptional.get().getName());
    }

    @Test
    @DisplayName("JUnit test for findAll method with pageable parameter")
    public void whenFindAllPagination_ThenReturnList() {
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
        testEntityManager.persist(book1);
        testEntityManager.persist(book2);
        testEntityManager.persist(book3);

        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
        Page<Book> bookPage = bookRepository.findAll(pageable);

        assertNotNull(bookPage.getContent());
        assertEquals(2, bookPage.getContent().size());
    }

    @Test
    @DisplayName("JUnit test for searchBooks method with pageable parameter")
    public void whenSearchBook_ThenReturnList() {
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
        testEntityManager.persist(book1);
        testEntityManager.persist(book2);
        testEntityManager.persist(book3);

        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
        Page<Book> bookPage = bookRepository.searchBooks("sach",0,pageable);
//        Page<Book> bookPage = bookRepository.searchBooks("tac gia",0,pageable);
//        Page<Book> bookPage = bookRepository.searchBooks("", 40_000, pageable);


        assertNotNull(bookPage.getContent());
        assertEquals(2, bookPage.getContent().size());
    }

    @Test
    @DisplayName("JUnit test for findByName method")
    public void whenFindByName_ThenReturnBookObject() {
        Optional<Book> book = bookRepository.findByName("Nhà giả kim");

        assertTrue(book.isPresent());
    }

    @Test
    @DisplayName("JUnit test for existsByName method")
    public void whenExistsByName_thenReturnTrue() {
        assertTrue(bookRepository.existsByName("Nhà giả kim"));
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
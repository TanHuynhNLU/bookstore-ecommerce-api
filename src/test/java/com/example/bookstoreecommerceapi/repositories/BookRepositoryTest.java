package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
                .price(70000)
                .genre("Tiểu thuyết")
                .build();
        testEntityManager.persist(book);
    }
    @Test
    @DisplayName("JUnit test for findAll method")
    public void whenFindAll_ThenReturnList(){
        Book book1 = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40000)
                .genre("Tiểu thuyết")
                .build();
        testEntityManager.persist(book1);

        assertNotNull(bookRepository.findAll());
        assertEquals(2,bookRepository.findAll().size());
    }
}
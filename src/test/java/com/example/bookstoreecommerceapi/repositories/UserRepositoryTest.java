package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.User;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .username("tanhuynh123")
                .password("12345678")
                .fullName("Huynh Ngoc Tan")
                .email("hntan2000@gmail.com").build();
        testEntityManager.persist(user);
    }

    @Test
    @DisplayName("JUnit test for findByUsername method")
    void whenFindByUsername_thenReturnUserObject() {
        User userActual = userRepository.findByUsername("tanhuynh123").get();
        System.out.println(userActual.getId());
        assertEquals(userActual.getUsername(), "tanhuynh123");
    }

    @Test
    @DisplayName("JUnit test for findAll method")
    void whenFindAll_thenReturnListUser() {
        User user1 = User.builder()
                .username("nguyenvana")
                .password("12345678")
                .fullName("Nguyen Van A")
                .email("nguyenvana@gmail.com").build();
        User user2 = User.builder()
                .username("nguyenvanb")
                .password("12345678")
                .fullName("Nguyen Van B")
                .email("nguyenvana@gmail.com").build();
        User user3 = User.builder()
                .username("nguyenvanb")
                .password("12345678")
                .fullName("Nguyen Van B")
                .email("nguyenvana@gmail.com").build();
        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
        Page<User> pageUser = userRepository.findAll(pageable);
        assertNotNull(pageUser.getContent());
        assertEquals(2, pageUser.getContent().size());

    }

    @Test
    @DisplayName("JUnit test for findById method")
    void whenFindById_thenReturnUserObject() {
        User userActual = userRepository.findById(1L).get();
        assertEquals(userActual.getUsername(), "Tan Huynh");
    }
}
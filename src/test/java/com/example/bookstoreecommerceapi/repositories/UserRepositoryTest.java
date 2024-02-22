package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.User;
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
    void whenFindByUsername_thenReturnUserObject(){
        User userActual = userRepository.findByUsername("tanhuynh123").get();
        assertEquals(userActual.getUsername(),"tanhuynh123");
    }
}
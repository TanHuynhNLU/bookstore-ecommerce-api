package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.repositories.UserRepository;
import com.example.bookstoreecommerceapi.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("tanhuynh123")
                .password("12345678")
                .fullName("Huynh Ngoc Tan")
                .email("hntan2000@gmail.com").build();
    }

    @Test
    @DisplayName("JUnit test for saveNewUser method")
    void whenAddNewUser_thenReturnUserObject() throws UserAlreadyExistsException {
        when(userRepository.save(user)).thenReturn(user);
        ResponseObject responseObject = userService.addNewUser(user);
        assertEquals(HttpStatus.CREATED, responseObject.getStatus());
    }

    @Test
    @DisplayName("JUnit test for getAllUsers method")
    void whenGetAll_thenReturnList() {
        User user2 = User.builder()
                .username("vanan123")
                .password("123456789")
                .fullName("Nguyen Van An")
                .email("nvan2004@gmail.com").build();
        List<User> mockUsers = List.of(user,user2);
        when(userRepository.findAll()).thenReturn(mockUsers);
        ResponseObject responseObject = userService.getAllUsers();
        List<User> actualUsers = (List<User>) responseObject.getData();
        assertNotNull(actualUsers);
        assertEquals(2,actualUsers.size());
    }
    @Test
    @DisplayName("JUnit test for getUserById method")
    void whenGetUserById_thenReturnUserObject(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        User userActual = userRepository.findById(1L).get();
        assertNotNull(userActual);
        assertEquals("tanhuynh123",userActual.getUsername());
    }
    @Test
    @DisplayName("JUnit test for deleteUser method")
    void whenDeleteUser_thenNothing(){
        long userId = user.getId();
        doNothing().when(userRepository).deleteById(userId);
        userRepository.deleteById(userId);
        verify(userRepository,times(1)).deleteById(userId);
    }
}
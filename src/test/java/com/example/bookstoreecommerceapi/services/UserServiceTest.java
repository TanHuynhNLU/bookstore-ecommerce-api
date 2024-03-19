package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.UserNotFoundException;
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
import org.springframework.data.domain.*;
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
        List<User> mockUsers = List.of(user, user2);
        when(userRepository.findAll()).thenReturn(mockUsers);
        ResponseObject responseObject = userService.getAllUsers();
        List<User> actualUsers = (List<User>) responseObject.getData();
        assertNotNull(actualUsers);
        assertEquals(2, actualUsers.size());
    }

    @Test
    @DisplayName("JUnit test for getUserById method")
    void whenGetUserById_thenReturnUserObject() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        User userActual = userRepository.findById(1L).get();
        assertNotNull(userActual);
        assertEquals("tanhuynh123", userActual.getUsername());
    }

    @Test
    @DisplayName("Junit test for getAllUsersPaginationAndSorting method")
    void whenGetAllUsersPaginationAndSorting_thenReturnList() {
        User user1 = User.builder()
                .username("nguyenvana")
                .password("12345678")
                .fullName("Nguyen Van A")
                .email("nguyenvana@gmail.com").build();
        User user2 = User.builder()
                .username("nguyenvanb")
                .password("12345678")
                .fullName("Nguyen Van B")
                .email("nguyenvanb@gmail.com").build();
        User user3 = User.builder()
                .username("nguyenvanc")
                .password("12345678")
                .fullName("Nguyen Van C")
                .email("nguyenvanc@gmail.com").build();
        List<User> users = List.of(user1, user2, user3);

        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "id");

        Page<User> mockedPage = new PageImpl<>(users);
        when(userRepository.findAll(pageable)).thenReturn(mockedPage);

        PaginationResponse res = userService.getAllUsersPaginationAndSorting(0, 3, "id");
        assertNotNull(res);
        assertEquals(3, res.getTotalItems());
    }

    @Test
    @DisplayName("Junit test for getUsersByUsernameContaining method")
    void whenGetUsersByUsernameContaining_thenReturnListUser() {
        User user1 = User.builder()
                .username("nguyenvana")
                .password("12345678")
                .fullName("Nguyen Van A")
                .email("nguyenvana@gmail.com").build();
        User user2 = User.builder()
                .username("nguyenvanb")
                .password("12345678")
                .fullName("Nguyen Van B")
                .email("nguyenvanb@gmail.com").build();
        User user3 = User.builder()
                .username("nguyenvanc")
                .password("12345678")
                .fullName("Nguyen Van C")
                .email("nguyenvanc@gmail.com").build();
        List<User> users = List.of(user1, user2, user3);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id ");

        Page<User> mockedPage = new PageImpl<>(users);
        when(userRepository.findByUsernameContainingIgnoreCase("nguyenvan", pageable)).thenReturn(mockedPage);

        PaginationResponse paginationResponse = userService.getUsersByUsernameContaining("nguyenvan", 0, 10, "id");
        assertNotNull(paginationResponse.getItems());
        assertEquals(3, paginationResponse.getTotalItems());
    }

    @Test
    @DisplayName("JUnit test for isUsernameExists method")
    void whenUserExist_thenReturnHttpStatusOk() {
        when(userRepository.existsByUsername("tanhuynh123")).thenReturn(true);
        ResponseObject actualResponseObject = userService.isUsernameExists("tanhuynh123");
        assertEquals(HttpStatus.OK, actualResponseObject.getStatus());
    }

    @Test
    @DisplayName("JUnit test for deleteUser method")
    void whenDeleteUser_thenNothing() throws UserNotFoundException {
        long userId = user.getId();
        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }
}
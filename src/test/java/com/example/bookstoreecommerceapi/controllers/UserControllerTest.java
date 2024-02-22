package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
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
    void addNewUser() throws Exception {
        User inputUser = User.builder()
                .username("tanhuynh123")
                .password("12345678")
                .fullName("Huynh Ngoc Tan")
                .email("hntan2000@gmail.com").build();
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED, "Thêm tài khoản thành công", user);
        Mockito.when(userService.addNewUser(inputUser)).thenReturn(responseObject);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "\t\"username\":\"tanhuynh123\",\n" +
                                "\t\"password\":\"12345678\",\n" +
                                "\t\"fullName\":\"Huynh Ngoc Tan\",\n" +
                                "\t\"email\":\"hntan2000@gmail.com\"\n" +
                                "}")
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value(user.getUsername()));
    }
    @Test
    void addNewUserExists() throws Exception {
        User inputUser = User.builder()
                .username("tanhuynh123")
                .password("12345678")
                .fullName("Huynh Ngoc Tan")
                .email("hntan2000@gmail.com").build();
        Mockito.when(userService.addNewUser(inputUser)).thenThrow(new UserAlreadyExistsException());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "\t\"username\":\"tanhuynh123\",\n" +
                                "\t\"password\":\"12345678\",\n" +
                                "\t\"fullName\":\"Huynh Ngoc Tan\",\n" +
                                "\t\"email\":\"hntan2000@gmail.com\"\n" +
                                "}")
                ).andExpect(status().isConflict());
    }

    @Test
    void getAllUsers() throws Exception {
        User user2 = User.builder()
                .username("vanan123")
                .password("123456789")
                .fullName("Nguyen Van An")
                .email("nvan2004@gmail.com").build();
        List<User> mockUsers = List.of(user, user2);
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Thành công", mockUsers);
        Mockito.when(userService.getAllUsers()).thenReturn(responseObject);
        mockMvc.perform(get("/api/users")).andExpect(status().isOk());
    }
}
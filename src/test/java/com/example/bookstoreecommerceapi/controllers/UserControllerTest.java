package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.UserNotFoundException;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.services.UserService;
import com.jayway.jsonpath.JsonPath;
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

    @Test
    void getUserById() throws Exception {
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Thành công", user);
        Mockito.when(userService.getUserById(1L)).thenReturn(responseObject);
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("tanhuynh123"));
    }

    @Test
    void getAllUsersPaginationAndSorting() throws Exception {
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
        List<User> users = List.of(user1,user2,user3);

        PaginationResponse paginationResponse = new PaginationResponse(3,users,1,0);
        Mockito.when(userService.getAllUsersPaginationAndSorting(0,3,"id")).thenReturn(paginationResponse);
        mockMvc.perform(get("/api/users/pagination?page=0&size=3&sort=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(3));
    }

    @Test
    void checkUsername() throws Exception {
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Tên đăng nhập đã tồn tại", null);
        Mockito.when(userService.isUsernameExists("tanhuynh123")).thenReturn(responseObject);
        mockMvc.perform(get("/api/users/check-username/tanhuynh123"))
                .andExpect(status().isOk());
    }
    @Test
    void updateUser() throws Exception {
        User userUpdate = User.builder()
                .fullName("Tan Huynh")
                .email("tanhuynh2000@gmail.com").build();
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Thành công", userUpdate);
        Mockito.when(userService.updateUser(1L,userUpdate)).thenReturn(responseObject);
        mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "\t\"fullName\":\"Tan Huynh\",\n" +
                        "\t\"email\":\"tanhuynh2000@gmail.com\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullName").value("Tan Huynh"));
    }
    @Test
    void deleteUser() throws Exception {
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Xóa tài khoản thành công", null);
        Mockito.when(userService.deleteUser(1L)).thenReturn(responseObject);

        mockMvc.perform(delete("/api/users/1")).andExpect(status().isOk());
    }
}
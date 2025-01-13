package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.ChangePasswordRequest;
import com.example.bookstoreecommerceapi.dto.LoginForm;
import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.IncorrectPasswordException;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.UserNotFoundException;
import com.example.bookstoreecommerceapi.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseObject getAllUsers();

    ResponseObject getUserById(long id) throws UserNotFoundException;

    PaginationResponse getAllUsersPaginationAndSorting(int page, int size, String sort);

    PaginationResponse getUsersByUsernameContaining(String username, int page, int size, String sort);

    ResponseObject isUsernameExists(String username);

    ResponseObject login(LoginForm loginForm);

    ResponseObject addNewUser(User newUser) throws UserAlreadyExistsException;

    ResponseObject updateUser(long id, User user) throws UserNotFoundException;

    ResponseObject changePassword(long id,ChangePasswordRequest request) throws UserNotFoundException, IncorrectPasswordException;

    ResponseObject deleteUser(long id) throws UserNotFoundException;

}

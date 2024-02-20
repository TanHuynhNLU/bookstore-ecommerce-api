package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseObject addNewUser(User newUser) throws UserAlreadyExistsException;

    ResponseObject getAllUsers();
}

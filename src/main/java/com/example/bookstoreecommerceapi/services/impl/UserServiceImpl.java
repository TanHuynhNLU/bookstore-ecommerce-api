package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.repositories.UserRepository;
import com.example.bookstoreecommerceapi.services.StorageService;
import com.example.bookstoreecommerceapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StorageService storageService;

    @Override
    public ResponseObject addNewUser(User newUser) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByUsername(newUser.getUsername());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("Tài khoản đã tồn tại");
        } else {
            userRepository.save(newUser);
            return new ResponseObject(HttpStatus.CREATED, "Thêm tài khoản thành công", newUser);
        }
    }

    @Override
    public ResponseObject getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseObject(HttpStatus.OK, "Thành công", users);
    }
}

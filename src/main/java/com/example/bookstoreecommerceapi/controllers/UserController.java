package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.UserNotFoundException;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllUsers() {
        ResponseObject responseObject = userService.getAllUsers();
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable long id) throws UserNotFoundException {
        ResponseObject responseObject = userService.getUserById(id);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addNewUser(@Valid @RequestBody User newUser) throws UserAlreadyExistsException {
        ResponseObject responseObject = userService.addNewUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable long id,@RequestBody User user) throws UserNotFoundException {
        ResponseObject responseObject = userService.updateUser( id,user);
        return ResponseEntity.ok(responseObject);
    }
}

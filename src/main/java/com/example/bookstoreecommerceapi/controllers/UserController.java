package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
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

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> getAllUsersPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        PaginationResponse paginationResponse = userService.getAllUsersPaginationAndSorting(page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<PaginationResponse> getUsersByUsernameContaining(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        PaginationResponse paginationResponse = userService.getUsersByUsernameContaining(username,page,size);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("check-username/{username}")
    public ResponseEntity<ResponseObject> checkUsername(@PathVariable String username) {
        ResponseObject responseObject = userService.isUsernameExists(username);
        if (responseObject.getStatus() == HttpStatus.OK)
            return ResponseEntity.ok(responseObject);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addNewUser(@Valid @RequestBody User newUser) throws UserAlreadyExistsException {
        ResponseObject responseObject = userService.addNewUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable long id, @RequestBody User user) throws UserNotFoundException {
        ResponseObject responseObject = userService.updateUser(id, user);
        return ResponseEntity.ok(responseObject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable long id) throws UserNotFoundException {
        ResponseObject responseObject = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
}

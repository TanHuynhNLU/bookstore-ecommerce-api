package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.LoginForm;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@Valid @RequestBody LoginForm loginForm) throws UserAlreadyExistsException {
        ResponseObject responseObject = userService.login(loginForm);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@Valid @RequestBody User newUser) throws UserAlreadyExistsException {
        ResponseObject responseObject = userService.addNewUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

}

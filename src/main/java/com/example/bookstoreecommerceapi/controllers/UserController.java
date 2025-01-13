package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.configs.JwtService;
import com.example.bookstoreecommerceapi.dto.ChangePasswordRequest;
import com.example.bookstoreecommerceapi.dto.LoginForm;
import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.IncorrectPasswordException;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.UserNotFoundException;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.services.UserService;
import com.example.bookstoreecommerceapi.services.impl.MyUserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getAllUsers() {
        ResponseObject responseObject = userService.getAllUsers();
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getUserById(@PathVariable long id) throws UserNotFoundException {
        ResponseObject responseObject = userService.getUserById(id);
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<PaginationResponse> getAllUsersPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        PaginationResponse paginationResponse = userService.getAllUsersPaginationAndSorting(page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<PaginationResponse> getUsersByUsernameContaining(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PaginationResponse paginationResponse = userService.getUsersByUsernameContaining(username, page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("check-username/{username}")
    @PermitAll
    public ResponseEntity<ResponseObject> checkUsername(@PathVariable String username) {
        ResponseObject responseObject = userService.isUsernameExists(username);
        if (responseObject.getStatus() == HttpStatus.OK)
            return ResponseEntity.ok(responseObject);
        else return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> updateUser(@PathVariable long id, @Valid @RequestBody User user) throws UserNotFoundException {
        ResponseObject responseObject = userService.updateUser(id, user);
        return ResponseEntity.ok(responseObject);
    }

    @PutMapping("/{id}/change-password")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> changePassword(@PathVariable long id,@RequestBody ChangePasswordRequest request) throws UserNotFoundException, IncorrectPasswordException {
        ResponseObject responseObject = userService.changePassword(id,request);
        return ResponseEntity.ok(responseObject);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable long id) throws UserNotFoundException {
        ResponseObject responseObject = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
}

package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.ContactNotFoundException;
import com.example.bookstoreecommerceapi.models.Contact;
import com.example.bookstoreecommerceapi.services.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/contacts")
@Tag(name = "Contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getAllContacts() {
        ResponseObject responseObject = contactService.getAllContacts();
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<PaginationResponse> getAllContactsPaginationAndSorting(@RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size,
                                                                                 @RequestParam(defaultValue = "id") String sort) {
        PaginationResponse paginationResponse = contactService.getAllContactsPaginationAndSorting(page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getContactById(@PathVariable long id) throws ContactNotFoundException {
        ResponseObject responseObject = contactService.getContactById(id);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping()
    public ResponseEntity<ResponseObject> addContact(@RequestBody Contact contact) {
        ResponseObject responseObject = contactService.addNewContact(contact);
        return ResponseEntity.ok(responseObject);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> updateContact(@PathVariable long id, @RequestBody Contact contact) throws ContactNotFoundException {
        ResponseObject responseObject = contactService.updateContact(id, contact);
        return ResponseEntity.ok(responseObject);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> updateContactPartially(@PathVariable long id, @RequestBody Map<String, Object> fields) throws ContactNotFoundException {
        ResponseObject responseObject = contactService.updateContactPartially(id, fields);
        return ResponseEntity.ok(responseObject);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> deleteContact(@PathVariable long id) throws ContactNotFoundException {
        ResponseObject responseObject = contactService.deleteContact(id);
        return ResponseEntity.ok(responseObject);
    }
}

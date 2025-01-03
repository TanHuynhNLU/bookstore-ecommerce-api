package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.exceptions.OrderNotFoundException;
import com.example.bookstoreecommerceapi.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
@Tag(name = "Order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getAllOrders() {
        ResponseObject responseObject = orderService.getAllOrders();
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<PaginationResponse> getAllOrdersPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PaginationResponse paginationResponse = orderService.getAllOrdersPaginationAndSorting(page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/chart-data")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getChartData() {
        ResponseObject responseObject = orderService.getChartData();
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable long id) throws OrderNotFoundException {
        ResponseObject responseObject = orderService.getOrderById(id);
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> getOrderByEmail(@RequestParam String email) throws OrderNotFoundException {
        ResponseObject responseObject = orderService.getOrderByEmail(email);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> addNewOrder(@RequestBody OrderRequest orderRequest) throws BookNotFoundException {
        ResponseObject responseObject = orderService.addNewOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(security = {@SecurityRequirement(name = "Bearer key")})
    public ResponseEntity<ResponseObject> updateOrderPartially(@PathVariable long id, @RequestBody Map<String, Object> fields) throws OrderNotFoundException {
        ResponseObject responseObject = orderService.updateOrderPartially(id, fields);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
}

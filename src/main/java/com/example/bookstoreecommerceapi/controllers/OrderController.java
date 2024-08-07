package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.exceptions.OrderNotFoundException;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseObject> getAllOrders() {
        ResponseObject responseObject = orderService.getAllOrders();
        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> getAllOrdersPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PaginationResponse paginationResponse = orderService.getAllOrdersPaginationAndSorting(page, size, sort);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable long id) throws OrderNotFoundException {
        ResponseObject responseObject = orderService.getOrderById(id);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping()
    public ResponseEntity<ResponseObject> addNewOrder(@RequestBody OrderRequest orderRequest) throws BookNotFoundException {
        ResponseObject responseObject = orderService.addNewOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateOrderPartially(@PathVariable long id, @RequestBody Map<String, Object> fields) throws  OrderNotFoundException {
        ResponseObject responseObject = orderService.updateOrderPartially(id, fields);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
}

package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping()
    public ResponseEntity<ResponseObject> getAllOrders(){
        ResponseObject responseObject = orderService.getAllOrders();
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping()
    public ResponseEntity<ResponseObject> addNewOrder (@RequestBody OrderRequest orderRequest) throws BookNotFoundException {
        ResponseObject responseObject = orderService.addNewOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }
}

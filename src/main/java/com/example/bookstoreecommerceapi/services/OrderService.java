package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.models.Order;

public interface OrderService {
    ResponseObject getAllOrders();

    ResponseObject addNewOrder(OrderRequest orderRequest) throws BookNotFoundException;
}

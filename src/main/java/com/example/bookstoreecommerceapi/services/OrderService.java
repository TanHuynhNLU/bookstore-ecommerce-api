package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.exceptions.OrderNotFoundException;

import java.util.Map;

public interface OrderService {
    ResponseObject getAllOrders();

    ResponseObject addNewOrder(OrderRequest orderRequest) throws BookNotFoundException;

    PaginationResponse getAllOrdersPaginationAndSorting(int page, int size, String sort);

    ResponseObject getOrderById(long id) throws OrderNotFoundException;

    ResponseObject updateOrderPartially(long id, Map<String, Object> fields) throws OrderNotFoundException;
}

package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.repositories.OrderRepository;
import com.example.bookstoreecommerceapi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseObject getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return new ResponseObject(HttpStatus.OK, "Thành công", orders);
    }
}

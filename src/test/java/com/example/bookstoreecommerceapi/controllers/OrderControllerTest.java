package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.models.Customer;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @MockBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;
    private Order order;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .email("customertesting@gmail.com")
                .phone("0123456789")
                .payment("Tiền mặt")
                .name("Customer Testing")
                .build();
        order = Order.builder()
                .customer(customer)
                .status("Đang xử lý")
                .build();
    }

    @Test
    void getALlOrders() throws Exception {
        Customer customer = Customer.builder()
                .email("customertesting1@gmail.com")
                .phone("0123456789")
                .payment("Tiền mặt")
                .name("Customer Testing 1")
                .build();
        Order order1 = Order.builder()
                .customer(customer)
                .status("Đang xử lý")
                .build();
        List<Order> mockOrders = List.of(order1, order1);

        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Thành công", mockOrders);
        when(orderService.getAllOrders()).thenReturn(responseObject);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(2));

    }
}
package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.OrderDetailRequest;
import com.example.bookstoreecommerceapi.dto.OrderRequest;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
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
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .email("customertesting@gmail.com")
                .phone("0123456789")
                .payment("Tiền mặt")
                .name("Customer Testing")
                .build();
        order = Order.builder()
                .customer(customer)
                .status("Đang xử lý")
                .dateCreated(new Date())
                .build();
    }

    @Test
    void getAllOrders() throws Exception {
        Customer customer1 = Customer.builder()
                .email("customertesting1@gmail.com")
                .phone("0123456789")
                .payment("Tiền mặt")
                .name("Customer Testing 1")
                .build();
        Order order1 = Order.builder()
                .customer(customer1)
                .status("Đang xử lý")
                .dateCreated(new Date())
                .build();
        List<Order> mockOrders = List.of(order, order1);

        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Thành công", mockOrders);
        when(orderService.getAllOrders()).thenReturn(responseObject);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(2));

    }

    @Test
    void addNewOrder() throws Exception {
        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder()
                .bookId(1l)
                .quantity(1)
                .build();
        OrderRequest orderRequest = OrderRequest.builder()
                .orderDetailRequests(List.of(orderDetailRequest))
                .customer(customer)
                .build();
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK, "Thành công", order);
        when(orderService.addNewOrder(orderRequest)).thenReturn(responseObject);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"customer\":{\n" +
                                "        \"name\":\"customertesting@gmail.com\",\n" +
                                "        \"email\":\"customertesting@gmail.com\",\n" +
                                "        \"phone\":\"0123456789\",\n" +
                                "        \"payment\":\"Tiền mặt\",\n" +
                                "        \"name\":\"Customer Testing\"\n" +
                                "    },\n" +
                                "    \"orderDetailRequests\":\n" +
                                "    [\n" +
                                "        {\"bookId\":1,\"quantity\":1}\n" +
                                "    ]\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.customer.name").value("Customer Testing"));

    }
}
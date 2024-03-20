package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.models.Customer;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.repositories.OrderRepository;
import com.example.bookstoreecommerceapi.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
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
    @DisplayName("JUnit test for getAllOrders method")
    void whenGetAllOrders_thenReturnList(){
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
        List<Order> mockOrders = List.of(order1,order1);
        when(orderRepository.findAll()).thenReturn(mockOrders);
        ResponseObject responseObject = orderService.getAllOrders();
        List<Order> actualOrders = (List<Order>) responseObject.getData();
        assertNotNull(actualOrders);
        assertEquals(2,actualOrders.size());
    }
}
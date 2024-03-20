package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Customer;
import com.example.bookstoreecommerceapi.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .email("customertesting@gmail.com")
                .phone("0123456789")
                .payment("Tiền mặt")
                .name("Customer Testing")
                .build();
        Order order = Order.builder()
                .customer(customer)
                .status("Đang xử lý")
                .build();
        testEntityManager.persist(order);
    }
    @Test
    @DisplayName("JUnit test for findAll method")
    void whenFindAll_thenReturnList(){
        Customer customer = Customer.builder()
                .email("customertesting1@gmail.com")
                .phone("0123456789")
                .payment("Tiền mặt")
                .name("Customer Testing 1")
                .build();
        Order order = Order.builder()
                .customer(customer)
                .status("Đang xử lý")
                .build();
        testEntityManager.persist(order);

        List<Order> actualOrders = orderRepository.findAll();
        assertNotNull(actualOrders);
        assertEquals(2, actualOrders.size());
    }
}
package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.models.Customer;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.models.OrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    private Order order;
    private Book book;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .email("customertesting1@gmail.com")
                .phone("0123456789")
                .payment("Tiền mặt")
                .name("Customer Testing 1")
                .build();
        order = Order.builder()
                .customer(customer)
                .status("Đang xử lý")
                .build();
        book = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        testEntityManager.persist(order);
        testEntityManager.persist(book);
    }
    @Test
    @DisplayName("JUnit test for save method")
    void whenSave_thenReturnOrderDetailObject(){
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .book(book)
                .quantity(1)
                .build();
        OrderDetail actualOrderDetail = orderDetailRepository.save(orderDetail);
        assertNotNull(actualOrderDetail);
    }
}
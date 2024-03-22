package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Customer;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.models.TimeLineEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TimeLineEntryRepositoryTest {
    @Autowired
    private TimeLineEntryRepository timeLineEntryRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    private Order order;

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

        testEntityManager.persist(order);
    }

    @Test
    @DisplayName("JUnit test for save method")
    void whenSave_thenReturnTimeLineEntryObject() {
        TimeLineEntry timeLineEntry = TimeLineEntry.builder()
                .order(order)
                .event("Đặt hàng trên shop")
                .dateCreated(new Date())
                .build();
        TimeLineEntry actualTimeLineEntry = timeLineEntryRepository.save(timeLineEntry);
        assertNotNull(actualTimeLineEntry);
    }
}
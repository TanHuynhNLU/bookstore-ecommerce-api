package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.OrderDetailRequest;
import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.models.*;
import com.example.bookstoreecommerceapi.repositories.BookRepository;
import com.example.bookstoreecommerceapi.repositories.OrderDetailRepository;
import com.example.bookstoreecommerceapi.repositories.OrderRepository;
import com.example.bookstoreecommerceapi.repositories.TimeLineEntryRepository;
import com.example.bookstoreecommerceapi.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TimeLineEntryRepository timeLineEntryRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private BookRepository bookRepository;
    private Order order;
    private Book book;
    private OrderDetail orderDetail;
    private Customer customer;
    private TimeLineEntry timeLineEntry;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .email("customertesting@gmail.com")
                .phone("0123456789")
                .payment("Tiền mặt")
                .name("Customer Testing")
                .build();

         book = Book.builder()
                .name("Chuyện con mèo dạy hải âu bay")
                .id(1L)
                .author("Luis Sepúlveda")
                .price(40_000)
                .genre("Tiểu thuyết")
                .build();
        orderDetail = OrderDetail.builder()
                .book(book)
                .quantity(1)
                .order(order)
                .build();
        order = Order.builder()
                .customer(customer)
                .status("Đang xử lý")
                .dateCreated(new Date())
                .build();
       timeLineEntry = TimeLineEntry.builder()
                .event(customer.getName() + " đặt hàng trên shop")
                .dateCreated(new Date())
                .order(order)
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

    @Test
    @DisplayName("JUnit test for addNewOrder method")
    void whenAddNewOrder_thenReturnOrderObject() throws BookNotFoundException {
        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder()
                .bookId(1L)
                .quantity(1)
                .build();
        OrderRequest orderRequest = OrderRequest.builder()
                .customer(customer)
                .orderDetailRequests(List.of(orderDetailRequest))
                .build();
        when(orderRepository.save(order)).thenReturn(order);
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);
        when(timeLineEntryRepository.save(timeLineEntry)).thenReturn(timeLineEntry);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        ResponseObject responseObject = orderService.addNewOrder(orderRequest);
        System.out.println(responseObject);
        Order actualOrder = (Order) responseObject.getData();
        assertNotNull(actualOrder);
        assertEquals("Customer Testing", actualOrder.getCustomer().getName());
    }
}
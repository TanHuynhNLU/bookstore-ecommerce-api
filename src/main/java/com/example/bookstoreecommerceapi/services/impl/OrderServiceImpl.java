package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.OrderDetailRequest;
import com.example.bookstoreecommerceapi.dto.OrderRequest;
import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.BookNotFoundException;
import com.example.bookstoreecommerceapi.models.Book;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.models.OrderDetail;
import com.example.bookstoreecommerceapi.models.TimeLineEntry;
import com.example.bookstoreecommerceapi.repositories.BookRepository;
import com.example.bookstoreecommerceapi.repositories.OrderDetailRepository;
import com.example.bookstoreecommerceapi.repositories.OrderRepository;
import com.example.bookstoreecommerceapi.repositories.TimeLineEntryRepository;
import com.example.bookstoreecommerceapi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private TimeLineEntryRepository timeLineEntryRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseObject getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return new ResponseObject(HttpStatus.OK, "Thành công", orders);
    }

    @Transactional
    @Override
    public ResponseObject addNewOrder(OrderRequest orderRequest) throws BookNotFoundException {
        Date currentDate = new Date();
        Order order = Order.builder()
                .customer(orderRequest.getCustomer())
                .status("Đang xử lý")
                .dateCreated(currentDate)
                .build();
        Order savedOrder = orderRepository.save(order);
        TimeLineEntry timeLineEntry = TimeLineEntry.builder()
                .event(orderRequest.getCustomer().getName() + " đặt hàng trên shop")
                .dateCreated(currentDate)
                .order(savedOrder)
                .build();
        timeLineEntryRepository.save(timeLineEntry);
        if (orderRequest.getOrderDetailRequests() != null)
            for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequests()) {
                Optional<Book> optionalBook = bookRepository.findById(orderDetailRequest.getBookId());
                if (!optionalBook.isPresent()) throw new BookNotFoundException("Sách không tồn tại");
                else {
                    OrderDetail orderDetail = OrderDetail.builder()
                            .book(optionalBook.get())
                            .order(order)
                            .quantity(orderDetailRequest.getQuantity())
                            .build();
                    orderDetailRepository.save(orderDetail);
                }
            }
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED, "Thành công", savedOrder);
        return responseObject;
    }

    @Override
    public PaginationResponse getAllOrdersPaginationAndSorting(int page, int size, String sort) {
        Sort.Direction direction = sort.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String property = direction == Sort.Direction.DESC ? sort.substring(1) : sort;
        Pageable pageable = PageRequest.of(page, size, direction, property);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        PaginationResponse paginationResponse =
                new PaginationResponse(orderPage.getTotalElements(), orderPage.getContent(), orderPage.getTotalPages(), orderPage.getNumber());
        return paginationResponse;
    }
}

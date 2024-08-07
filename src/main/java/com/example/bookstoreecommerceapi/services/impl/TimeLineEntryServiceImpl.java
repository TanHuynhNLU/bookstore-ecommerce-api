package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.OrderNotFoundException;
import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.models.TimeLineEntry;
import com.example.bookstoreecommerceapi.repositories.OrderRepository;
import com.example.bookstoreecommerceapi.repositories.TimeLineEntryRepository;
import com.example.bookstoreecommerceapi.services.TimeLineEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class TimeLineEntryServiceImpl implements TimeLineEntryService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TimeLineEntryRepository timeLineEntryRepository;

    @Override
    public ResponseObject getAllTimeLineEntry(long id) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Collections.reverse(optionalOrder.get().getTimeLineEntries());
            return new ResponseObject(HttpStatus.OK, "Thành công", optionalOrder.get().getTimeLineEntries());
        }else {
            throw new OrderNotFoundException("Không tìm thấy đơn hàng");
        }
    }

    @Override
    public ResponseObject addTimeLineEntry(long orderId, TimeLineEntry timeLineEntry) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            timeLineEntry.setDateCreated(new Date());
            timeLineEntry.setOrder(order);
            timeLineEntryRepository.save(timeLineEntry);
            return new ResponseObject(HttpStatus.CREATED, "Thành công", timeLineEntryRepository.save(timeLineEntry));
        }else {
            throw new OrderNotFoundException("Không tìm thấy đơn hàng");
        }
    }
}

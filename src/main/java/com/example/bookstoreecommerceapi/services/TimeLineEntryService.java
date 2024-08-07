package com.example.bookstoreecommerceapi.services;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.OrderNotFoundException;
import com.example.bookstoreecommerceapi.models.TimeLineEntry;

public interface TimeLineEntryService {
    ResponseObject getAllTimeLineEntry(long orderId) throws OrderNotFoundException;

    ResponseObject addTimeLineEntry(long orderId, TimeLineEntry timeLineEntry) throws OrderNotFoundException;
}

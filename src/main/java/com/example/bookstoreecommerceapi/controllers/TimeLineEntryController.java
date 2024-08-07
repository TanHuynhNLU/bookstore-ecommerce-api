package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.OrderNotFoundException;
import com.example.bookstoreecommerceapi.models.TimeLineEntry;
import com.example.bookstoreecommerceapi.services.TimeLineEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TimeLineEntryController {
    @Autowired
    private TimeLineEntryService timeLineEntryService;

    @GetMapping("/orders/{orderId}/timeline-entries")
    public ResponseEntity<ResponseObject> getAllTimeLineEntry(@PathVariable long orderId) throws OrderNotFoundException {
        ResponseObject responseObject = timeLineEntryService.getAllTimeLineEntry(orderId);
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping("/orders/{orderId}/timeline-entries")
    public ResponseEntity<ResponseObject> addTimeLineEntry(@PathVariable long orderId, @RequestBody TimeLineEntry timeLineEntry) throws OrderNotFoundException {
        ResponseObject responseObject = timeLineEntryService.addTimeLineEntry(orderId, timeLineEntry);
        return ResponseEntity.ok(responseObject);
    }

}

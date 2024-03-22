package com.example.bookstoreecommerceapi.dto;

import com.example.bookstoreecommerceapi.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Customer customer;
    private List<OrderDetailRequest> orderDetailRequests;
}

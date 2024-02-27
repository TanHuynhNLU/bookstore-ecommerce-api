package com.example.bookstoreecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse {
    private long totalItems;
    private Object items;
    private int totalPages;
    private int currentPage;
}

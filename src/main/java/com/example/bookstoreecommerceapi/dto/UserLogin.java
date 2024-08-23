package com.example.bookstoreecommerceapi.dto;

import com.example.bookstoreecommerceapi.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogin {
    private User user;
    private String token;
}
